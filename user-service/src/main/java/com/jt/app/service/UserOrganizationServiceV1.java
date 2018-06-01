package com.jt.app.service;

import com.jt.app.repository.UserOrganizationRepository;
import com.jt.app.model.UserOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class UserOrganizationServiceV1 {

    @Autowired
    private UserOrganizationRepository userOrganizationRepository;

    /**
     * 获取用户组织架构
     *
     * @param id
     * @return
     */
    public UserOrganization getOrganizationById(Integer id) {
        return userOrganizationRepository.getOne(id);
    }

    public UserOrganizationRepository getOrganizationRepository() {
        return this.userOrganizationRepository;
    }

    /**
     * 添加用户组织架构
     *
     * @param userOrganization
     */
    public void addOrganization(UserOrganization userOrganization) {
        userOrganizationRepository.save(userOrganization);
    }

    /**
     * 更新用户组织架构
     *
     * @param create_Time
     * @param update_time
     * @param id
     * @return
     */
    @Transactional
    public Integer updateUserOrganization(Date create_Time, Date update_time, Integer id) {
        return userOrganizationRepository.updateUserOrganization(create_Time, update_time, id);
    }
}
