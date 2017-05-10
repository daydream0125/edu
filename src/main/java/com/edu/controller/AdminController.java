package com.edu.controller;

import com.edu.service.AdminService;
import com.edu.utils.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dev on 2017/5/9.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @RequestMapping("")
    public String admin() {
        return "admin/admin";
    }

    @RequestMapping("/logs")
    @ResponseBody
    public List getLogs(Page page) {
        return adminService.getLogs(page);
    }

    @RequestMapping("/logsCount")
    @ResponseBody
    public Long getLogsCount() {
        return adminService.getLogsCount();
    }
}
