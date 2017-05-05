package com.edu.model;



import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
public class Role {
    private int roleId;
    private String roleName;
    private String roleDescription;
    private Date createTime;
    private Boolean status;
    private Account createUser;

    @Id
    @Column(name = "roleID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "roleName", nullable = true, length = 32)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "roleDescription", nullable = true, length = 2147483647)
    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    @Basic
    @Column(name = "createTime", nullable = true)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (roleId != role.roleId) return false;
        if (roleName != null ? !roleName.equals(role.roleName) : role.roleName != null) return false;
        if (roleDescription != null ? !roleDescription.equals(role.roleDescription) : role.roleDescription != null)
            return false;
        if (createTime != null ? !createTime.equals(role.createTime) : role.createTime != null) return false;
        if (status != null ? !status.equals(role.status) : role.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId;
        result = 31 * result + (roleName != null ? roleName.hashCode() : 0);
        result = 31 * result + (roleDescription != null ? roleDescription.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "createUserID", referencedColumnName = "userID")
    public Account getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Account accountByCreateUserId) {
        this.createUser = accountByCreateUserId;
    }
}
