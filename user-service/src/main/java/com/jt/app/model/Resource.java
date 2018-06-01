package com.jt.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: Administrator
 * @Date: 2018/5/19 16:42
 * @Description: 资源
 */
@Entity
@Table(name = "t_resource")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Resource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(length = 80, nullable = false, unique = true)
    private String resourceName;//资源名称
    @Column(length = 100)
    private String resourceUrl;//资源地址
    @Column(length = 10)
    private Integer parentId;//上级编号
    @Column(columnDefinition = "tinyint(1) DEFAULT 1 COMMENT '资源状态：1正常、0禁用'")
    private Integer resourceType = 1;//资源类型:1页面、2按钮
    @Column(length = 100)
    private String logoUrl;
    @Column(length = 1)
    private Integer resourceStatus;
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

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Integer getResourceStatus() {
        return resourceStatus;
    }

    public void setResourceStatus(Integer resourceStatus) {
        this.resourceStatus = resourceStatus;
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

    public Resource() {
    }

    public Resource(Integer id, String resourceName, String resourceUrl, Integer parentId, Integer resourceType,
                    String logoUrl, Integer resourceStatus, Date createTime, String createUser, Date updateTime,
                    String updateUser) {
        this.id = id;
        this.resourceName = resourceName;
        this.resourceUrl = resourceUrl;
        this.parentId = parentId;
        this.resourceType = resourceType;
        this.logoUrl = logoUrl;
        this.resourceStatus = resourceStatus;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", resourceName='" + resourceName + '\'' +
                ", resourceUrl='" + resourceUrl + '\'' +
                ", parentId=" + parentId +
                ", resourceType=" + resourceType +
                ", logoUrl='" + logoUrl + '\'' +
                ", resourceStatus=" + resourceStatus +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", updateTime=" + updateTime +
                ", updateUser='" + updateUser + '\'' +
                '}';
    }
}
