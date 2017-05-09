package com.edu.controller;

import com.edu.annotation.SystemControllerLog;
import com.edu.model.ChapterContent;
import com.edu.model.Clazz;
import com.edu.model.Course;
import com.edu.model.CourseChapter;
import com.edu.service.CourseService;
import com.edu.service.TeacherService;
import com.edu.utils.model.SectionList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Date;
import java.util.List;

/*/
    addXXX 为转向新增页面，saveXXX 为实际执行的 save 方法
 */
@Controller
@RequestMapping("/teacher/course")
public class CourseController {
    private TeacherService teacherService;
    private CourseService courseService;


    public CourseService getCourseService() {
        return courseService;
    }

    @Resource
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    public TeacherService getTeacherService() {
        return teacherService;
    }

    @Resource
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @SystemControllerLog(description = "查看所有课程")
    @RequestMapping("/courseList")
    public String courseList(String teacherId, Model model) {
        model.addAttribute("courseList", courseService.getCoursesByTeacherId(teacherId));
        return "teacher/course/courseList";
    }

    @RequestMapping("/addCourse")
    public String addCourse() {
        return "teacher/course/addCourse";
    }

    @SystemControllerLog(description = "添加课程")
    @RequestMapping("/saveCourse")
    //保存课程，使用 springMVC 提供的文件上传方法上传课程图片
    public String saveCourse(Course course, String teacherId, @RequestParam(value = "coursePic", required = false) MultipartFile multipartFile) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            byte b[] = new byte[inputStream.available()];
            inputStream.read(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        courseService.saveCourse(course, teacherId);
        return "mainmenu";
    }


    @RequestMapping("/coursePhoto")
    public void showCoursePhoto(int courseId, HttpServletResponse httpServletResponse) {
        Course course = courseService.getCourseByCourseId(courseId);
        try {
            OutputStream outputStream = httpServletResponse.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SystemControllerLog(description = "查看课程信息")
    @RequestMapping("/courseInfo")
    public ModelAndView courseInfo(int courseId) {
        ModelAndView modelAndView = new ModelAndView("teacher/course/courseInfo");
        modelAndView.addObject("courseInfo", courseService.getCourseByCourseId(courseId));
        modelAndView.addObject("classes", courseService.getClazzByCourseId(courseId));
        return modelAndView;
    }

    @RequestMapping("/addClass")
    public ModelAndView addClass(int courseId) {
        ModelAndView modelAndView = new ModelAndView("teacher/course/addClass");
        modelAndView.addObject("course", courseService.getCourseByCourseId(courseId));
        return modelAndView;
    }

    @SystemControllerLog(description = "增加班级")
    //保存班级信息
    @RequestMapping("/saveClass")
    public ModelAndView saveClass(Clazz clazz, String teacherId, int courseId) {
        ModelAndView modelAndView = new ModelAndView("mainmenu");
        clazz.setCreateTime(new Date(System.currentTimeMillis()));
        courseService.saveClass(clazz, teacherId, courseId);
        return modelAndView;
    }


    @SystemControllerLog(description = "开放课程注册权限")
    //更新班级信息，开放学生注册
    @RequestMapping("/openRegistration")
    public String openRegistration(int classId, Boolean isPublicRegister, String teacherId) {
        courseService.updateIsPublicRegister(classId, isPublicRegister);
        return "redirect:courseList?teacherId=" + teacherId;
    }


    //接受更新课程信息的请求，返回待修改课程的原本信息，并允许用户在此基础上做必要的更新
    @RequestMapping("/updatePage")
    public ModelAndView updatePage(int courseId) {
        ModelAndView modelAndView = new ModelAndView("teacher/course/updateCourse");
        modelAndView.addObject("course", courseService.getCourseByCourseId(courseId));
        return modelAndView;
    }

    @SystemControllerLog(description = "更新课程信息")
    @RequestMapping("/updateCourse")
    public String updateCourse(Course course, String teacherId) {
        courseService.updateCourse(course);
        return "redirect: courseList?teacherId=" + teacherId;
    }

    @RequestMapping("/courseChapterInfo")
    public String courseContentInfo(int courseId, Model model) {
        List chapters = courseService.getCourseChapterByCourseId(courseId);
        model.addAttribute("chapters", chapters);
        model.addAttribute("courseId", courseId);
        return "teacher/course/courseChapterInfo";
    }

    @RequestMapping("/addCourseChapter")
    public String addCourseContentPage(int courseId, Model model) {
        model.addAttribute("course", courseService.getCourseByCourseId(courseId));
        return "teacher/course/addCourseChapter";
    }


    //选择更新小节信息时，还是要根据 courseChapter 进行更新，因为小节原本可能没有信息，这时属于新增操作。courseChapter 和 chapterContent 一一对应
    //可根据 courseChapter 对 chapterContent 进行更新
    @RequestMapping("/chapterContent")
    public String chapterContent(int chapterId, Model model) {
        model.addAttribute("chapter", courseService.getCourseChapterById(chapterId));
        return "teacher/course/chapterContent";
    }

    @RequestMapping("/updateChapterContentPage")
    public String updateChapterContentPage(int chapterId, Model model) {
        model.addAttribute("chapter", courseService.getCourseChapterById(chapterId));
        return "teacher/course/updateChapterContent";
    }


    @RequestMapping("updateChapterContent1")
    public String updateChapterContent(String content, int chapterId, Model model) {
        CourseChapter chapter = courseService.getCourseChapterById(chapterId);
        ChapterContent chapterContent = courseService.getChapterContentByChapterId(chapterId);
        //检查数据库有无该记录，若没有，则新增，若有则更新
        if (chapterContent == null) {
            chapterContent = new ChapterContent();
            chapterContent.setContent(content);
            chapterContent.setCourseChapter(chapter);
            courseService.saveChapterContent(chapterContent);
        } else {
            chapterContent.setContent(content);
            courseService.updateChapterContent(chapterContent);
        }
        model.addAttribute("chapter", courseService.getCourseChapterById(chapterId));
        return "teacher/course/chapterContent";
    }


}
