package com.edu.security;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.annotation.SystemControllerLog;
import com.edu.dao.AccountDAO;
import com.edu.model.Account;
import com.edu.service.AccountMsgService;
import com.edu.utils.HttpUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//参考自：https://www.kancloud.cn/digest/springsecurity/169116
public class SimpleLoginSuccessHandler implements AuthenticationSuccessHandler, InitializingBean {

    protected Log logger = LogFactory.getLog(getClass());

    private String defaultTargetUrl;

    private boolean forwardToDestination = false;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Resource
    private AccountMsgService accountMsgService;




    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        this.saveLoginInfo(request, authentication);

        if (this.forwardToDestination) {
            logger.info("Login success,Forwarding to " + this.defaultTargetUrl);

            request.getRequestDispatcher(this.defaultTargetUrl).forward(request, response);
        } else {
            logger.info("Login success,Redirecting to " + this.defaultTargetUrl);

            this.redirectStrategy.sendRedirect(request, response, this.defaultTargetUrl);
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void saveLoginInfo(HttpServletRequest request, Authentication authentication) {
        Account account;
        User user= (User) authentication.getPrincipal();
        try {
            //从spring security中获得用户id，然后从数据库中取出该account，更新相关信息，放入session中
            account = accountMsgService.getAccountById(user.getUsername());
            String ip = HttpUtils.getRealIP(request);
            //将该次登录时间和登录ip写入数据库
            account.setLastLoginTime(new Date());
            account.setLoginIp(ip);
            accountMsgService.updateAccount(account);
        } catch (DataAccessException e) {
            if (logger.isWarnEnabled()) {
                logger.info("无法更新用户登录信息至数据库");
            }
        }
    }



    public void setDefaultTargetUrl(String defaultTargetUrl) {
        this.defaultTargetUrl = defaultTargetUrl;
    }

    public void setForwardToDestination(boolean forwardToDestination) {
        this.forwardToDestination = forwardToDestination;
    }


    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
