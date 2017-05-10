package com.edu.controller;

import com.edu.annotation.SystemControllerLog;
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
public class CourseController {
    @Resource
    private CourseService courseService;
    @Resource
    private ClazzService clazzService;


    @SystemControllerLog("访问课程首页")
    @RequestMapping("{courseId}")
    public String course(@PathVariable("courseId") int courseId,Model model) {
        model.addAttribute("courseId",courseId);
        return "course/course";
    }

    @SystemControllerLog("查看课程信息")
    @RequestMapping(value = "info/{courseId}",method = RequestMethod.GET)
    @ResponseBody
    public Course getCourseInfo(@PathVariable("courseId") int courseId) {
        return courseService.getCourseByCourseId(courseId);
    }


    /**
     * 获取课程的简单信息，包括 courseName，courseDesc，
     * @param courseId
     * @return
     */
    @RequestMapping("sharp/{courseId}")
    @ResponseBody
    public List getSharpCourse(@PathVariable("courseId") int courseId) {
        return courseService.getSharpCourse(courseId);
    }

    @SystemControllerLog("获取参加课程的学生数")
    @RequestMapping(value = "{courseId}/studentNum",method = RequestMethod.GET)
    @ResponseBody
    public Long getStudentsNum(@PathVariable("courseId") int courseId) {
        return courseService.getStudentsdNumByCourseId(courseId);
    }


    @SystemControllerLog("查看课程下开设的班级")
    @RequestMapping(value = "{courseId}/clazzes",method = RequestMethod.GET)
    @ResponseBody
    public List getClazzesByCourseId(@PathVariable("courseId")int courseId) {
        return courseService.getClazzByCourseId(courseId);
    }

    @SystemControllerLog("查看用户是否注册班级")
    @RequestMapping(value = "isRegister",method = RequestMethod.GET)
    @ResponseBody
    public boolean checkIsRegister(String userId,int classId) {
        return clazzService.isRegister(classId, userId);
    }

    @SystemControllerLog("用户注册班级")
    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ResponseBody
    public boolean register(String userId,int classId,int courseId) {
        return clazzService.register(userId, classId,courseId);
    }

    //返回页面
    @SystemControllerLog("访问课程内容页面")
    @RequestMapping("{courseId}/courseChapterInfo")
    public String courseChapterInfoPage(@PathVariable("courseId") int courseId, Model model) {
        model.addAttribute("courseId",courseId);
        return "course/chapters";
    }

    @SystemControllerLog("获取课程章节信息")
    @RequestMapping(value = "{courseId}/chapters",method = RequestMethod.GET)
    @ResponseBody
    public List getChaptersByCourseId(@PathVariable("courseId") int courseId) {
        return courseService.getCourseChapterByCourseId(courseId);
    }

    @SystemControllerLog("访问小节内容页面")
    @RequestMapping("chapterContentPage/{sectionId}")
    public String chapterContentPage(@PathVariable("sectionId") int sectionId,Model model) {
        model.addAttribute("sectionId",sectionId);
        return "course/chapterContent";
    }

    @SystemControllerLog("获取小节内容")
    @RequestMapping(value = "chapterContent/{sectionId}",method = RequestMethod.GET)
    @ResponseBody
    public ChapterContent getChapterContent(@PathVariable("sectionId") int sectionId) {
        return courseService.getChapterContentByChapterId(sectionId);
    }


    @SystemControllerLog("获取课程章信息")
    @RequestMapping("courseId/{chapterId}")
    @ResponseBody
    public int getCourseIdByChapterId(@PathVariable("chapterId") int chapterId) {
        return courseService.getCourseIdByChapterId(chapterId);
    }


    @RequestMapping("courseName/{courseId}")
    @ResponseBody
    public String getCourseName(@PathVariable("courseId") int courseId) {
        return courseService.getCourseName(courseId);
    }

}
