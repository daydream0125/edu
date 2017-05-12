package com.edu.controller;


import com.edu.annotation.SystemControllerLog;
import com.edu.model.Account;
import com.edu.model.UserInfo;
import com.edu.service.AccountMsgService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

@Controller
public class LoginController {

    private AccountMsgService accountMsgService;
    private Producer captchaProducer;

    public Producer getCaptchaProducer() {
        return captchaProducer;
    }

    @Resource
    public void setCaptchaProducer(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    public AccountMsgService getAccountMsgService() {
        return accountMsgService;
    }

    @Resource
    public void setAccountMsgService(AccountMsgService accountMsgService) {
        this.accountMsgService = accountMsgService;
    }

    @SystemControllerLog("进入登陆界面")
    @RequestMapping("/sign")
    public String login() {
        return "user/sign";
    }


    //没有通过spring security，提示错误信息
    @SystemControllerLog("登陆错误")
    @RequestMapping("/loginError")
    public String handleLoginError() {
        return "redirect:sign?error=true";
    }


    //注销，向前台传递注销成功信息
    @SystemControllerLog("注销成功")
    @RequestMapping("/logout")
    public String logout() {
        return "redirect:sign?message=You successfully logout.";
    }


    @SystemControllerLog("注册")
    @RequestMapping("/register")
    @ResponseBody
    public String addUser(String username, String password, String code, HttpSession session) {
        //验证码填写错误
        if (! code.equalsIgnoreCase((String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY)))
            return "codeError";
        //注册账号已注册
        if (accountMsgService.accountIsExist(username))
            return "accountExistError";
        //保存账号信息
        accountMsgService.saveAccount(username,password);
        return "成功";
    }


    //生成验证码，并存入session中
    @RequestMapping(value = "/captcha-image")
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String code = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String capText = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }


    @RequestMapping("/updateInfo")
    public String updateUserInfoPage() {
        return "user/updateUserInfo";
    }

    @RequestMapping("/userInfo")
    public String userInfoPage() {
        return "user/userInfo";
    }

    @RequestMapping("/userInfo/{userId}")
    @ResponseBody
    public Account getUserInfo(@PathVariable("userId") String userId) {
        return accountMsgService.getAccountById(userId);
    }

    @SystemControllerLog("更新个人信息")
    @RequestMapping("/updateUserInfo")
    @ResponseBody
    public boolean updateUserInfo(UserInfo userInfo,String cardNumber) {
        return accountMsgService.updateUserInfo(userInfo, cardNumber);
    }


    @RequestMapping("/access_denied")
    public String accessDenied() {
        return "access_denied";
    }
}
