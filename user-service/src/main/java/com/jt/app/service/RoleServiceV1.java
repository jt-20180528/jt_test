package com.jt.app.service;

import com.jt.app.repository.RoleRepository;
import com.jt.app.repository.UserRoleRepository;
import com.jt.app.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoleServiceV1 {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * 获取用户
     *
     * @param id
     * @return
     */
    public Role getRoleById(Integer id) {
        return roleRepository.getOne(id);
    }

    public RoleRepository getRoleRepository() {
        return this.roleRepository;
    }

    /**
     * 添加用户
     *
     * @param role
     */
    public void addRole(Role role) {
        roleRepository.save(role);
    }

    /**
     * 更新用户
     *
     * @param create_Time
     * @param update_time
     * @param id
     * @return
     */
    @Transactional
    public Integer updateRole(Date create_Time, Date update_time, Integer id) {
        return roleRepository.updateRole(create_Time, update_time, id);
    }

    public List<Role> getRolesByIds(Integer userId) {
        List<Integer> roleIds = userRoleRepository.getRoleIdsByUserId(userId);
        List<Role> roles = new ArrayList<Role>();
        for (Integer roldId : roleIds) {
            roles.add(roleRepository.getById(roldId));
        }
        return roles;
    }

    public Long getRoleCount() {
        return getRoleRepository().count();
    }

}
