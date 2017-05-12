package com.edu.service;

import com.edu.annotation.SystemServiceLog;
import com.edu.dao.AccountDAO;
import com.edu.dao.RoleDAO;
import com.edu.dao.UserInfoDAO;
import com.edu.dao.UserRoleDAO;
import com.edu.model.Account;
import com.edu.model.Role;
import com.edu.model.UserInfo;
import com.edu.utils.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Transactional
public class AccountMsgService {
    private AccountDAO accountDAO;
    private UserInfoDAO userInfoDAO;
    private UserRoleDAO userRoleDAO;
    private Md5PasswordEncoder md5PasswordEncoder;
    private RoleDAO roleDAO;

    public RoleDAO getRoleDAO() {
        return roleDAO;
    }
    @Resource
    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    @Resource
    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public UserInfoDAO getUserInfoDAO() {
        return userInfoDAO;
    }

    @Resource
    public void setUserInfoDAO(UserInfoDAO userInfoDAO) {
        this.userInfoDAO = userInfoDAO;
    }

    public UserRoleDAO getUserRoleDAO() {
        return userRoleDAO;
    }

    @Resource
    public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
        this.userRoleDAO = userRoleDAO;
    }

    public Md5PasswordEncoder getMd5PasswordEncoder() {
        return md5PasswordEncoder;
    }

    @Resource
    public void setMd5PasswordEncoder(Md5PasswordEncoder md5PasswordEncoder) {
        this.md5PasswordEncoder = md5PasswordEncoder;
    }

    public boolean accountIsExist(String userId) {
        return accountDAO.findByUserId(userId) != null;
    }

    /*
    用于用户注册，完成以下工作：
     1、同时向数据库account、userInfo写入账号信息
     2、对该用户赋予ROLE_USER角色，即凡注册用户均有ROLE_USER权限。
     */
    @SystemServiceLog("用户注册")
    public void saveAccount(String userId, String password) {
        Account account = new Account(userId, md5PasswordEncoder.encode(password));
        account.setCreateTime(new Date());
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        account.setUserInfo(userInfo);
        account.getRoles().add(roleDAO.findByRoleName("ROLE_USER"));
        accountDAO.save(account);
    }

    public Account getAccountById(String userId) {
        return accountDAO.findByUserId(userId);
    }

    @SystemServiceLog("更新账户")
    public void updateAccount(Account account) {
        accountDAO.update(account);
    }

    @SystemServiceLog("添加学生角色")
    public void setRoleStudent(Account account) {
        Role role = roleDAO.findByRoleName("ROLE_STUDENT");
        account.getRoles().add(role);
        accountDAO.update(account);
    }

    @SystemServiceLog("更新用户信息")
    public boolean updateUserInfo(UserInfo userInfo,String cardNumber) {
        Account account = accountDAO.findByUserId(userInfo.getUserId());
        UserInfo info = userInfoDAO.findByUserId(userInfo.getUserId());
        if (account != null) {
            account.setName(userInfo.getName());
            account.setCardNumber(cardNumber);
            //更新学号则增加学生角色
            if (cardNumber != null) {
                account.getRoles().add(roleDAO.findByRoleName("ROLE_STUDENT"));
            }
            accountDAO.update(account);
            if (info != null) {
                info.setDesc(userInfo.getDesc());
                info.setEmail(userInfo.getEmail());
                info.setNickName(userInfo.getNickName());
                info.setPhone(userInfo.getPhone());
                info.setTelephone(userInfo.getTelephone());
                userInfo.setUserId("");
                userInfoDAO.update(info);
            } else return false;
        } else {
            return false;
        }
        return true;
    }

}
