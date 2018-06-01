package com.jt.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lj
 * @Date: 2018/5/19 16:55
 * @Description: 用户职位
 */
@Entity
@Table(name = "t_userJob")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class UserJob implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(length = 1)
    private Integer userId;
    @Column(length = 1)
    private Integer jobId;
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

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
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

    public UserJob() {
    }

    public UserJob(Integer id, Integer userId, Integer jobId, Date createTime, String createUser, Date updateTime,
                   String updateUser) {
        this.id = id;
        this.userId = userId;
        this.jobId = jobId;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        return "UserJob{" +
                "id=" + id +
                ", userId=" + userId +
                ", jobId=" + jobId +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", updateTime=" + updateTime +
                ", updateUser='" + updateUser + '\'' +
                '}';
    }
}
