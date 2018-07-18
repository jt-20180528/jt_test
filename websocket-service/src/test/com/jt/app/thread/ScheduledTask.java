package com.jt.app.thread;

import com.jt.app.thread.model.ScheduledEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class ScheduledTask implements Callable<ScheduledEntity> {

    private final static Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    public ScheduledEntity scheduledEntity;
    private final String scheduledProfire = "--ScheduledTask--";
    private CountDownLatch countDownLatch;

    public ScheduledTask(ScheduledEntity scheduledEntity, CountDownLatch countDownLatch) {
        this.scheduledEntity = scheduledEntity;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public ScheduledEntity call() throws Exception {
        Object obj = null;
        logger.info("定时任务第{}正在处理...", countDownLatch.getCount());
        if (scheduledEntity != null) {
            scheduledEntity.setAddress(scheduledEntity.getAddress() + scheduledProfire);
            scheduledEntity.setName(scheduledEntity.getName() + scheduledProfire);
            scheduledEntity.setTelephone(scheduledEntity.getTelephone() + scheduledProfire);
            obj = scheduledEntity;
        }
        countDownLatch.countDown();
        return (ScheduledEntity) obj;
    }
}
