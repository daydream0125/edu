package com.edu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("teacher/score")
public class ScoreController {

    @RequestMapping("/scoreManage")
    public String scoreManage() {
        return "teacher/score/scoreManage";
    }

}
