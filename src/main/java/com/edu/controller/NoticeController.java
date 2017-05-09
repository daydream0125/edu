package com.edu.controller;

import com.edu.model.Notice;
import com.edu.service.NoticeService;
import com.edu.utils.UploadFile;
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


    @RequestMapping("/notice")
    public String noticeIndex() {
        return "notice/notice";
    }

    @RequestMapping("/noticeInfo/{id}")
    public String noticeInfo(@PathVariable("id") int noticeId, Model model) {
        model.addAttribute("noticeId",noticeId);
        return "notice/noticeInfo";
    }

    @RequestMapping("/addNotice")
    public String addNotice() {
        return "notice/addNotice";
    }

    @RequestMapping("/notices")
    @ResponseBody
    public List getNotices() {
        return noticeService.getAllNotice();
    }

    @RequestMapping(value = "/notice/{id}",method = RequestMethod.GET)
    @ResponseBody
    public List getNoticeInfo(@PathVariable("id") int id) {
        return noticeService.getNoticeInfoById(id);
    }

    @RequestMapping("/notice/delete")
    @ResponseBody
    public boolean deleteNotice(int id) {
        return noticeService.deleteNotice(id);
    }

    /**
     * 上传图片至 notice-img下
     *
     */
    @RequestMapping("/notice/uploadPicture")
    @ResponseBody
    public String uploadProblemPic(@RequestParam("pic") MultipartFile multipartFile) {
        return UploadFile.uploadFile(multipartFile,"notice-img/");
    }

    @RequestMapping("/notice/add")
    @ResponseBody
    public boolean addNotice(Notice notice,String userId) {
        return noticeService.addNotice(notice,userId);
    }
}
