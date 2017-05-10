package com.edu.controller;

import com.edu.annotation.SystemControllerLog;
import com.edu.dao.ProblemDAO;
import com.edu.model.Course;
import com.edu.service.CourseService;
import com.edu.service.ExerciseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
public class IndexController {
    @Resource
    private CourseService courseService;
    @Resource
    private ProblemDAO problemDAO;
    @Resource
    private ExerciseService exerciseService;

    @SystemControllerLog("访问首页")
    @RequestMapping(value = {"/", "/index"})
    public String getAllCourses() {
        return "index";
    }

    @RequestMapping("/courses")
    @ResponseBody
    public List getCourses() {
        return courseService.getAllCourse();
    }
}
