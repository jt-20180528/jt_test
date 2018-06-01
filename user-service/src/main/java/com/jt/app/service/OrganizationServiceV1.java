package com.jt.app.service;

import com.jt.app.repository.OrganizationRepository;
import com.jt.app.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class OrganizationServiceV1 {

    @Autowired
    private OrganizationRepository organizationRepository;

    /**
     * 获取组织架构
     *
     * @param id
     * @return
     */
    public Organization getOrganizationById(Integer id) {
        return organizationRepository.getOne(id);
    }

    public OrganizationRepository getOrganizationRepository() {
        return this.organizationRepository;
    }

    /**
     * 添加组织架构
     *
     * @param organization
     */
    public void addOrganization(Organization organization) {
        organizationRepository.save(organization);
    }

    /**
     * 更新组织架构
     *
     * @param create_Time
     * @param update_time
     * @param id
     * @return
     */
    @Transactional
    public Integer updateOrganization(Date create_Time, Date update_time, Integer id) {
        return organizationRepository.updateOrganization(create_Time, update_time, id);
    }
}
