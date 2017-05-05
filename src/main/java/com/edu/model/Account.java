package com.edu.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
public class Account {
    private String userId;
    private String password;
    private String name;
    private String cardNumber;
    private Boolean type = false;
    private Boolean state = true;
    private String loginIp;
    private String bindIp;
    private Boolean isBindIp;
    private Byte tryTimes;
    private Date lastLoginTime;
    private Date createTime;
    private UserInfo userInfo;
    private Set<Role> roles = new HashSet<>();
    public Account() {
    }

    public Account(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    @Id
    @Column(name = "userID", nullable = false, length = 32)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "cardNumber", nullable = true, length = 32)
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Basic
    @Column(name = "type", nullable = true)
    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    @Basic
    @Column(name = "state", nullable = true)
    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @Basic
    @Column(name = "loginIP", nullable = true, length = 64)
    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    @Basic
    @Column(name = "bindIP" , nullable = true, length = 64)
    public String getBindIp() {
        return bindIp;
    }
    public void setBindIp(String bindIp) {
        this.bindIp = bindIp;
    }

    @Basic
    @Column(name = "isBindIP")
    public Boolean getIsBindIp() { return isBindIp;}
    public void setIsBindIp(Boolean isBindIp) {
        this.isBindIp = isBindIp;
    }


    @Basic
    @Column(name = "tryTimes", nullable = true)
    public Byte getTryTimes() {
        return tryTimes;
    }

    public void setTryTimes(Byte tryTimes) {
        this.tryTimes = tryTimes;
    }

    @Basic
    @Column(name = "lastLoginTime", nullable = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Basic
    @Column(name = "createTime", nullable = true)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (userId != null ? !userId.equals(account.userId) : account.userId != null) return false;
        if (password != null ? !password.equals(account.password) : account.password != null) return false;
        if (name != null ? !name.equals(account.name) : account.name != null) return false;
        if (cardNumber != null ? !cardNumber.equals(account.cardNumber) : account.cardNumber != null) return false;
        if (type != null ? !type.equals(account.type) : account.type != null) return false;
        if (state != null ? !state.equals(account.state) : account.state != null) return false;
        if (loginIp != null ? !loginIp.equals(account.loginIp) : account.loginIp != null) return false;
        if (bindIp != null ? !bindIp.equals(account.bindIp) : account.bindIp != null) return false;
        if (isBindIp != null ? !isBindIp.equals(account.isBindIp) : account.isBindIp != null) return false;
        if (tryTimes != null ? !tryTimes.equals(account.tryTimes) : account.tryTimes != null) return false;
        if (lastLoginTime != null ? !lastLoginTime.equals(account.lastLoginTime) : account.lastLoginTime != null)
            return false;
        if (createTime != null ? !createTime.equals(account.createTime) : account.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (loginIp != null ? loginIp.hashCode() : 0);
        result = 31 * result + (bindIp != null ? bindIp.hashCode() : 0);
        result = 31 * result + (isBindIp != null ? isBindIp.hashCode() : 0);
        result = 31 * result + (tryTimes != null ? tryTimes.hashCode() : 0);
        result = 31 * result + (lastLoginTime != null ? lastLoginTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userID")
    @JsonManagedReference
    public UserInfo getUserInfo() {
        return userInfo;
    }
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "userID"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
