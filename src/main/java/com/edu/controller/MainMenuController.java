package com.edu.controller;


import com.edu.annotation.SystemControllerLog;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;


@Controller

public class MainMenuController {

    @RequestMapping("/submitCode")
    public String submitCode() {
        return "submitCode";
    }

    @SystemControllerLog(description = "查看个人信息")
    @RequestMapping("/userInfo")
    public ModelAndView userInfo(HttpServletRequest request) {
        //获取 spring security 上下文
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        // 获得当前用户所拥有的权限
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) securityContextImpl
                .getAuthentication().getAuthorities();

        ModelAndView modelAndView = new ModelAndView("user/userInfo");
        modelAndView.addObject("authorities",authorities);
        return modelAndView;
    }


}
