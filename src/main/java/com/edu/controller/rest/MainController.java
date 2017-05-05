package com.edu.controller.rest;

import com.edu.service.CourseService;
import org.springframework.stereotype.Controller;


import javax.annotation.Resource;


@Controller
public class MainController {

    @Resource
    private CourseService courseService;


//    @RequestMapping(value = "/courses",method = RequestMethod.GET)
//    @ResponseBody
//    public List getCourses(HttpServletResponse response) {
//        //response.setHeader("Access-Control-Allow-Origin", "*");
//        return courseService.getAllCourse();
//    }

//    //处理图片请求，直接将二进制写回 jsp
//    @RequestMapping(value = "/coursePhoto/{courseId}",method = RequestMethod.GET)
//    public void showCoursePhoto(@PathVariable int courseId, HttpServletResponse httpServletResponse) {
//        Course course = courseService.getCourseByCourseId(courseId);
//        try {
//            OutputStream outputStream = httpServletResponse.getOutputStream();
//            outputStream.write(course.getCoursePhoto());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
