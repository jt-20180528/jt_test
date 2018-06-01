package com.jt.app.service;

import com.jt.app.model.UserJob;
import com.jt.app.repository.UserJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class UserJobServiceV1 {

    @Autowired
    private UserJobRepository userJobRepository;

    /**
     * 获取用户职位
     *
     * @param id
     * @return
     */
    public UserJob getUserById(Integer id) {
        return userJobRepository.getOne(id);
    }

    public UserJobRepository getUserRepository() {
        return this.userJobRepository;
    }

    /**
     * 添加用户职位
     *
     * @param userJob
     */
    public void addUser(UserJob userJob) {
        userJobRepository.save(userJob);
    }

    /**
     * 更新用户职位
     *
     * @param create_Time
     * @param update_time
     * @param id
     * @return
     */
    @Transactional
    public Integer updateUser(Date create_Time, Date update_time, Integer id) {
        return userJobRepository.updateUserJob(create_Time, update_time, id);
    }
}
