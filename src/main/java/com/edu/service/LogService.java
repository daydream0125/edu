package com.edu.service;

import com.edu.dao.LogDAO;
import com.edu.model.Log;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * Created by dev on 2017/5/11.
 */
@Service
@Transactional
public class LogService {

    @Resource
    private LogDAO logDAO;

    public  void saveLog(Log log) {
        logDAO.save(log);
    }
}
