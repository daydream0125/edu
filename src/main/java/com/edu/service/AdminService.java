package com.edu.service;

import com.edu.annotation.SystemServiceLog;
import com.edu.dao.LogDAO;
import com.edu.utils.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by dev on 2017/5/9.
 */
@Service
@Transactional
public class AdminService {
    @Resource
    private LogDAO logDAO;

    @SystemServiceLog("获取日志")
    public List getLogs(Page page) {
        return logDAO.getLogs(page);
    }

    @SystemServiceLog("获取日志数量")
    public Long getLogsCount() {
        return logDAO.getLogsCount();
    }
}
