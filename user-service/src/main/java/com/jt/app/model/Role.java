package com.jt.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: lj
 * @Date: 2018/5/19 16:38
 * @Description: 角色
 */
@Entity
@Table(name = "t_role")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(length = 80, nullable = false, unique = true)
    private String roleName;            //角色名称
    @Column(columnDefinition = "tinyint(1) DEFAULT 1 COMMENT '角色状态：1正常、0禁用'")
    private Integer roleStatus = 1;    //角色状态:1正常、0禁用 默认为1
    private Date createTime;            //创建时间
    @Column(length = 20)
    private String createUser;          //创建人
    private Date updateTime;            //修改时间
    @Column(length = 20)
    private String updateUser;          //修改人

    public Role() {
    }

    public Role(Integer id, String roleName, Integer roleStatus, Date createTime, String createUser,
                Date updateTime, String updateUser) {
        this.id = id;
        this.roleName = roleName;
        this.roleStatus = roleStatus;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleStatus() {
        return roleStatus;
    }

    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
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

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", roleStatus=" + roleStatus +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", updateTime=" + updateTime +
                ", updateUser='" + updateUser + '\'' +
                '}';
    }
}
