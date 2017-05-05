package com.edu.controller;

import com.edu.model.ChapterContent;
import com.edu.model.Course;
import com.edu.service.ClazzService;
import com.edu.service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.jws.WebParam;
import java.util.List;

@Controller
@RequestMapping("/course/")
public class CourseController2 {
    @Resource
    private CourseService courseService;
    @Resource
    private ClazzService clazzService;


    @RequestMapping("{courseId}")
    public String course(@PathVariable("courseId") int courseId,Model model) {
        model.addAttribute("courseId",courseId);
        return "course/course";
    }

    @RequestMapping(value = "info/{courseId}",method = RequestMethod.GET)
    @ResponseBody
    public Course getCourseInfo(@PathVariable("courseId") int courseId) {
        return courseService.getCourseByCourseId(courseId);
    }

    @RequestMapping(value = "{courseId}/studentNum",method = RequestMethod.GET)
    @ResponseBody
    public Long getStudentsNum(@PathVariable("courseId") int courseId) {
        return courseService.getStudentsdNumByCourseId(courseId);
    }


    @RequestMapping(value = "{courseId}/clazzes",method = RequestMethod.GET)
    @ResponseBody
    public List getClazzesByCourseId(@PathVariable("courseId")int courseId) {
        return courseService.getClazzByCourseId(courseId);
    }

    @RequestMapping(value = "isRegister",method = RequestMethod.GET)
    @ResponseBody
    public boolean checkIsRegister(String userId,int classId) {
        return clazzService.isRegister(classId, userId);
    }

    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ResponseBody
    public boolean register(String userId,int classId,int courseId) {
        return clazzService.register(userId, classId,courseId);
    }

    //返回页面
    @RequestMapping("{courseId}/courseChapterInfo")
    public String courseChapterInfoPage(@PathVariable("courseId") int courseId, Model model) {
        model.addAttribute("courseId",courseId);
        return "course/chapters";
    }

    @RequestMapping(value = "{courseId}/chapters",method = RequestMethod.GET)
    @ResponseBody
    public List getChaptersByCourseId(@PathVariable("courseId") int courseId) {
        return courseService.getCourseChapterByCourseId(courseId);
    }

    @RequestMapping("chapterContentPage/{sectionId}")
    public String chapterContentPage(@PathVariable("sectionId") int sectionId,Model model) {
        model.addAttribute("sectionId",sectionId);
        return "course/chapterContent";
    }

    @RequestMapping(value = "chapterContent/{sectionId}",method = RequestMethod.GET)
    @ResponseBody
    public ChapterContent getChapterContent(@PathVariable("sectionId") int sectionId) {
        return courseService.getChapterContentByChapterId(sectionId);
    }

    @RequestMapping("courseId/{chapterId}")
    @ResponseBody
    public int getCourseIdByChapterId(@PathVariable("chapterId") int chapterId) {
        return courseService.getCourseIdByChapterId(chapterId);
    }

}
