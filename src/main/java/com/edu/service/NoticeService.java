package com.edu.service;

import com.edu.dao.AccountDAO;
import com.edu.dao.NoticeDAO;
import com.edu.model.Account;
import com.edu.model.Notice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

/**
 * Created by dev on 2017/5/7.
 */
@Service
@Transactional
public class NoticeService {
    @Resource
    private NoticeDAO noticeDAO;
    @Resource
    private AccountDAO accountDAO;

    public List getAllNotice() {
        return noticeDAO.getAllNotice();
    }

    public List getNoticeInfoById(int id) {
        return noticeDAO.get(id);
    }

    public boolean deleteNotice(int id) {
        try {
            noticeDAO.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addNotice(Notice notice, String userId) {
        try {
            notice.setAccount(accountDAO.getByUserId(userId));
            notice.setIssueTime(new Date(System.currentTimeMillis()));
            noticeDAO.save(notice);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
