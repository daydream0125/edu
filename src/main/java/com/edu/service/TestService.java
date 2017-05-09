package com.edu.service;

import com.edu.annotation.SystemServiceLog;
import com.edu.dao.CourseDAO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * Created by dev on 2017/5/8.
 */
@Service
@Transactional
public class TestService {
    @Resource
    private CourseDAO courseDAO;

    @SystemServiceLog(description = "fuck")
    public void test(int id) {
        System.out.println("this is service test" + id);
    }
}
