package com.jt.app.thread;

import com.jt.app.thread.model.ThreadEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class ThreadTask implements Callable<ThreadEntity> {

    private final static Logger logger = LoggerFactory.getLogger(ThreadTask.class);

    private CountDownLatch countDownLatch;
    private static final String threadStr = "----thread";
    private ThreadEntity threadEntity;

    public ThreadTask(CountDownLatch countDownLatch, ThreadEntity threadEntity) {
        this.countDownLatch = countDownLatch;
        this.threadEntity = threadEntity;
    }

    @Override
    public ThreadEntity call() throws Exception {
        logger.info("正在处理倒数第{}个任务！",countDownLatch.getCount());
        Object object = null;
        try {
            if (threadEntity != null) {
                threadEntity.setAddress(this.threadEntity.getAddress() + threadStr);
                threadEntity.setName(threadEntity.getName() + threadStr);
                threadEntity.setTelephone(threadEntity.getTelephone() + threadStr);
                object = threadEntity;
            }
            countDownLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ThreadEntity)object;
    }
}
