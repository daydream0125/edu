package com.edu.controller;

import com.edu.annotation.SystemControllerLog;
import com.edu.dao.ProblemDAO;
import com.edu.model.Account;
import com.edu.model.ChapterContent;
import com.edu.service.CourseService;
import com.edu.service.TestService;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@Controller
public class TestController {
    @Resource
    private CourseService courseService;
    @Resource
    private ProblemDAO problemDAO;
    @Resource
    private TestService testService;
    @RequestMapping("/test")
    public String page() {
        return "test/test";
    }

    @RequestMapping("/videoTest")
   public void uploadVideo(@RequestParam("video")MultipartFile multipartFile) throws IOException {
       String name = multipartFile.getOriginalFilename();
       String path = "/Users/hanhao0125/Documents/edu-video/" + name + "1";
       File file = new File(path);
       multipartFile.transferTo(file);
   }

   @RequestMapping("/playVideo")
   public String palyVideo() {
        return "test/playVideo";
   }



   @RequestMapping(value = "/testAjax",method = RequestMethod.POST)
   @ResponseBody
   public String testAjax(Account account,String info) {
       System.out.println(account.getPassword());
       return "success";
   }


   @RequestMapping("/addTest")
   @ResponseBody
   public String addChpaterContent() {
        for (int i = 15; i  <= 41; i++) {
            ChapterContent chapterContent = new ChapterContent();
            chapterContent.setCourseChapter(courseService.getCourseChapterById(i));
            courseService.saveChapterContent(chapterContent);
        }
        return "success";
   }

   @RequestMapping("testLogin")
   public String loginTest() {
        return "user/sign";
   }


   @SystemControllerLog("测试")
   @RequestMapping("/testLog/{id}")
   public void testLog(@PathVariable("id") int id) {
        testService.test(id);
    }





}
