package com.edu.controller;

import com.edu.annotation.SystemControllerLog;
import com.edu.model.Notice;
import com.edu.service.NoticeService;
import com.edu.utils.UploadFile;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;


@Controller
public class NoticeController {
    @Resource
    private NoticeService noticeService;


    @SystemControllerLog("访问通知公告首页")
    @RequestMapping("/notice")
    public String noticeIndex() {
        return "notice/notice";
    }

    @SystemControllerLog("访问通知公告内容")
    @RequestMapping("/noticeInfo/{id}")
    public String noticeInfo(@PathVariable("id") int noticeId, Model model) {
        model.addAttribute("noticeId",noticeId);
        return "notice/noticeInfo";
    }

    @SystemControllerLog("访问添加通知公告页面")
    @Secured({"ROLE_TEACHER","ROLE_ADMIN"})
    @RequestMapping("/addNotice")
    public String addNotice() {
        return "notice/addNotice";
    }

    @SystemControllerLog("查看所有通知公告")
    @RequestMapping("/notices")
    @ResponseBody
    public List getNotices() {
        return noticeService.getAllNotice();
    }

    @SystemControllerLog("获取通知公告内容")
    @RequestMapping(value = "/notice/{id}",method = RequestMethod.GET)
    @ResponseBody
    public List getNoticeInfo(@PathVariable("id") int id) {
        return noticeService.getNoticeInfoById(id);
    }

    @Secured({"ROLE_TEACHER","ROLE_ADMIN"})
    @SystemControllerLog("删除通知公告")
    @RequestMapping("/notice/delete")
    @ResponseBody
    public boolean deleteNotice(int id) {
        return noticeService.deleteNotice(id);
    }

    /**
     * 上传图片至 notice-img下
     *
     */
    @Secured({"ROLE_TEACHER","ROLE_ADMIN"})
    @SystemControllerLog("上传通知公告图片")
    @RequestMapping("/notice/uploadPicture")
    @ResponseBody
    public String uploadProblemPic(@RequestParam("pic") MultipartFile multipartFile) {
        return UploadFile.uploadFile(multipartFile,"notice-img/");
    }

    @Secured({"ROLE_TEACHER","ROLE_ADMIN"})
    @SystemControllerLog("添加通知公告")
    @RequestMapping("/notice/add")
    @ResponseBody
    public boolean addNotice(Notice notice,String userId) {
        return noticeService.addNotice(notice,userId);
    }
}
