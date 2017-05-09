package com.edu.controller;

import com.edu.service.ExerciseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by dev on 2017/5/6.
 */
@Controller
@RequestMapping("/exercise")
public class ExerciseController2 {
    @Resource
    private ExerciseService exerciseService;

    @RequestMapping("/{exerciseId}/isJudge")
    @ResponseBody
    public boolean isExerciseFinishJudge(@PathVariable("exerciseId") int exerciseId) {
        return exerciseService.isExercciseFinishJudge(exerciseId);
    }
}
