package com.jt.app.service;

import com.jt.app.model.RoleResource;
import com.jt.app.repository.RoleResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class RoleResourceServiceV1 {

    @Autowired
    private RoleResourceRepository roleResourceRepository;

    /**
     * 获取用户资源权限
     *
     * @param id
     * @return
     */
    public RoleResource getRoleResourceById(Integer id) {
        return roleResourceRepository.getOne(id);
    }

    public RoleResourceRepository getRoleResourceRepository() {
        return this.roleResourceRepository;
    }

    /**
     * 添加用户资源权限
     *
     * @param roleResource
     */
    public void addRoleResource(RoleResource roleResource) {
        roleResourceRepository.save(roleResource);
    }

    /**
     * 更新用户资源权限
     *
     * @param create_Time
     * @param update_time
     * @param id
     * @return
     */
    @Transactional
    public Integer updateRoleResource(Date create_Time, Date update_time, Integer id) {
        return roleResourceRepository.updateRoleResource(create_Time, update_time, id);
    }

}
