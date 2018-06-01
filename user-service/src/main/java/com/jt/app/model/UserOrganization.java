package com.jt.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lj
 * @Date: 2018/5/19 16:53
 * @Description: 用户组织架构/部门
 */
@Entity
@Table(name = "t_userOrganization")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class UserOrganization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(length = 10)
    private Integer userId;
    @Column(length = 10)
    private Integer organizationId;
    private Date createTime;
    @Column(length = 20)
    private String createUser;
    private Date updateTime;
    @Column(length = 20)
    private String updateUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public UserOrganization() {
    }

    public UserOrganization(Integer id, Integer userId, Integer organizationId, Date createTime,
                            String createUser, Date updateTime, String updateUser) {
        this.id = id;
        this.userId = userId;
        this.organizationId = organizationId;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        return "UserOrganization{" +
                "id=" + id +
                ", userId=" + userId +
                ", organizationId=" + organizationId +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", updateTime=" + updateTime +
                ", updateUser='" + updateUser + '\'' +
                '}';
    }
}
