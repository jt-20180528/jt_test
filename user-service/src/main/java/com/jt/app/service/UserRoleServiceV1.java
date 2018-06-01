package com.jt.app.service;

import com.jt.app.repository.UserRoleRepository;
import com.jt.app.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class UserRoleServiceV1 {

    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * 获取用户角色
     *
     * @param id
     * @return
     */
    public UserRole getUserById(Integer id) {
        return userRoleRepository.getOne(id);
    }

    public UserRoleRepository getUserRoleRepository() {
        return this.userRoleRepository;
    }

    /**
     * 添加用户角色
     *
     * @param userRole
     */
    public void addUserRole(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

    /**
     * 更新用户角色
     *
     * @param create_Time
     * @param update_time
     * @param id
     * @return
     */
    @Transactional
    public Integer updateUser(Date create_Time, Date update_time, Integer id) {
        return userRoleRepository.updateUser(create_Time, update_time, id);
    }
}
