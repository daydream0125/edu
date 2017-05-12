package com.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dev on 2017/5/10.
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/404")
    public String page404() {
        return "error/404";
    }

    @RequestMapping("/exception")
    public String pageException() {
        return "error/exception";
    }

    @RequestMapping("/session_error")
    public String pageSessionError() {
        return "error/sessionError";
    }

}
