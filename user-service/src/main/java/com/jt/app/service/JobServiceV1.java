package com.jt.app.service;

import com.jt.app.repository.JobRepository;
import com.jt.app.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class JobServiceV1 {

    @Autowired
    private JobRepository jobRepository;

    /**
     * 获取职位
     *
     * @param id
     * @return
     */
    public Job getJobById(Integer id) {
        return jobRepository.getOne(id);
    }

    public JobRepository getJobRepository() {
        return this.jobRepository;
    }

    /**
     * 添加职位
     *
     * @param job
     */
    public void addJob(Job job) {
        jobRepository.save(job);
    }

    /**
     * 更新职位
     *
     * @param create_Time
     * @param update_time
     * @param id
     * @return
     */
    @Transactional
    public Integer updateJob(Date create_Time, Date update_time, Integer id) {
        return jobRepository.updateJob(create_Time, update_time, id);
    }
}
