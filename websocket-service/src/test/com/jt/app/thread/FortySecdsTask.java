package com.jt.app.thread;

import com.jt.app.thread.model.ScheduledEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.*;

public class FortySecdsTask implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(FortySecdsTask.class);

    public ScheduledEntity scheduledEntity;
    private final String scheduledProfire = "--FortySecdsTask--";
    private CountDownLatch countDownLatch;

    public FortySecdsTask(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        logger.info("60秒定时查询定时任务开始...");
        List<ScheduledEntity> scheduledEntitys = (List<ScheduledEntity>) GlobleCache.getTaskCache("ScheduledTaskKey");
        List<ScheduledEntity> scheduledEntityList = new ArrayList<>(scheduledEntitys.size());
        if (scheduledEntitys != null && scheduledEntitys.size() > 0) {
            for (ScheduledEntity scheduledEntity : scheduledEntitys) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.SECOND, 61);
                Calendar calendar2 = Calendar.getInstance();
                calendar2.add(Calendar.SECOND, 122);
                if (scheduledEntity.getStartTime().after(calendar.getTime()) && scheduledEntity.getStartTime().before(calendar2.getTime())) {
                    scheduledEntityList.add(scheduledEntity);
                }
            }
        }
        if (scheduledEntityList != null && scheduledEntityList.size() > 0) {
            logger.info("一共有{}个定时任务,准备开始执行！", scheduledEntityList.size());
            scheduledTask(scheduledEntityList);
        } else {
            logger.info("沒有可执行的任务！");
        }
        countDownLatch.countDown();
    }

    public void scheduledTask(List<ScheduledEntity> scheduledEntity) {
        ThreadServer.scheduledTask(scheduledEntity);
    }
}
