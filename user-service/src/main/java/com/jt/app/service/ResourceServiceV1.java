package com.jt.app.service;

import com.jt.app.repository.ResourceRepository;
import com.jt.app.repository.RoleResourceRepository;
import com.jt.app.model.Resource;
import com.jt.app.model.Role;
import com.jt.app.model.RoleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ResourceServiceV1 {

    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private RoleResourceRepository roleResourceRepository;

    /**
     * 获取资源
     *
     * @param id
     * @return
     */
    public Resource getResourceById(Integer id) {
        return resourceRepository.getOne(id);
    }

    public ResourceRepository getResourceRepository() {
        return this.resourceRepository;
    }

    /**
     * r
     * 添加资源
     *
     * @param resource
     */
    public void addResource(Resource resource) {
        resourceRepository.save(resource);
    }

    /**
     * 更新资源
     *
     * @param create_Time
     * @param update_time
     * @param id
     * @return
     */
    @Transactional
    public Integer updateResource(Date create_Time, Date update_time, Integer id) {
        return resourceRepository.updateResource(create_Time, update_time, id);
    }

    /**
     * 获取资源信息
     *
     * @param roles
     * @return
     */
    public List<Resource> getResourcesByIds(List<Role> roles) {
        List<RoleResource> roleResources = new ArrayList<RoleResource>();
        for (Role role : roles) {
            roleResources = roleResourceRepository.getByRoleId(role.getId());
            if (roleResources != null && roleResources.size() > 0) {
                break;
            }
        }
        List<Resource> resources = new ArrayList<Resource>();
        for (RoleResource roleResource : roleResources) {
            Resource resource = resourceRepository.getById(roleResource.getResourceId());
            if (resource != null) {
                resources.add(resource);
            }
        }
        return resources;
    }

    public Long getResourceCount() {
        return this.getResourceRepository().count();
    }
}
