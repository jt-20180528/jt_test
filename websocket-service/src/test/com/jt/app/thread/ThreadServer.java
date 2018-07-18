package com.jt.app.thread;

import com.jt.app.thread.model.ScheduledEntity;
import com.jt.app.thread.model.ThreadEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

public class ThreadServer {

    private final static Logger logger = LoggerFactory.getLogger(ThreadServer.class);

    private static CountDownLatch countDownLatch = null;
    private static ExecutorService executorService = null;
    private static int initCoreThreadNum = Runtime.getRuntime().availableProcessors() * 2;

    public static void main(String[] args) {
        //testThread();
        testScheduledThread();
        fortySecdsScheduledTask();
    }

    public static void testThread() {
        long startTime = 0L, endTime = 0L;

        final int listSize = 100000;
        try {
            //1：创造实体对象数组
            List<ThreadEntity> entities = getEntityLists(listSize);

            //2：创建线程池
            initCoreThreadNum = getInitCoreThreadNum(listSize);
            executorService = Executors.newFixedThreadPool(initCoreThreadNum, new ThreadFactory(ThreadEvents.award));
            countDownLatch = new CountDownLatch(listSize);

            //3：执行线程并接受返回对象
            List<Future<ThreadEntity>> entitys = new ArrayList<>(listSize + 1);
            startTime = System.currentTimeMillis();
            logger.info("开始线程任务！");
            for (ThreadEntity threadEntity : entities) {
                ThreadTask threadTask = new ThreadTask(countDownLatch, threadEntity);
                Future<ThreadEntity> entity = executorService.submit(threadTask);
                entitys.add(entity);
            }

            countDownLatch.await();

            if (entitys != null) {
                for (Future<ThreadEntity> threadEntityFuture : entitys) {
                    ThreadEntity threadEntity = threadEntityFuture.get();
                    //logger.info(threadEntity.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        endTime = System.currentTimeMillis();
        logger.info("处理{}个线程任务一共耗时：{}毫秒", listSize, (endTime - startTime));
    }

    public static void testScheduledThread() {
        //创建定时任务，每1分钟创建20个定时任务，一共创建20分钟的任务量
        final int listSize = 200;
        logger.info("开始创建{}个定时任务！", listSize);
        List<ScheduledEntity> entities = getScheduledLists(listSize);
        GlobleCache.setTaskCache("ScheduledTaskKey", entities);
        logger.info("{}个定时任务,放入缓存成功！", listSize);
        //获取1分钟之内的定时任务
        List<ScheduledEntity> tasks = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 60);
        Calendar currCalendar = Calendar.getInstance();
        logger.info("开始过滤1分钟之内的定时任务！");
        for (ScheduledEntity scheduledEntity : entities) {
            if (scheduledEntity.getStartTime().before(calendar.getTime()) && scheduledEntity.getStartTime().after(currCalendar.getTime())) {
                tasks.add(scheduledEntity);
            }
        }
        logger.info("1分钟之内的定时任务一共有{}个！", tasks.size());

        //创建定时任务线程池并执行任务
        scheduledTask(tasks);
    }

    public static void fortySecdsScheduledTask() {
        logger.info("开始60秒定时任务处理！");
        try {
            //添加特殊定时任务，每隔40秒之后加载下一批定时任务
            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor(new ThreadFactory(ThreadEvents.fortySecdsTask));
            countDownLatch = new CountDownLatch(1);
            FortySecdsTask fortySecdsTask = new FortySecdsTask(countDownLatch);
            final long initialDelay = 5L;
            final long period = 60L;
            service.scheduleAtFixedRate(fortySecdsTask, initialDelay, period, TimeUnit.SECONDS);
            countDownLatch.await();
            logger.info("60秒定时任务处理完毕！");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<ThreadEntity> getEntityLists(int listSize) {
        ThreadEntity threadEntity = null;
        List<ThreadEntity> entities = new ArrayList<>(listSize + 1);
        for (int i = 0; i < listSize; i++) {
            threadEntity = new ThreadEntity();
            threadEntity.setTelephone(String.valueOf(System.currentTimeMillis()));
            threadEntity.setName("大军" + (i + 1));
            threadEntity.setAddress("广东广州" + (i + 1));
            entities.add(threadEntity);
        }
        return entities;
    }

    public static void scheduledTask(final List<ScheduledEntity> tasks){
        initCoreThreadNum = getInitCoreThreadNum(tasks.size());
        ScheduledExecutorService service = null;
        try {
            countDownLatch = new CountDownLatch(tasks.size());
            service = Executors.newScheduledThreadPool(initCoreThreadNum, new ThreadFactory(ThreadEvents.timerTask));
            List<Future<ScheduledEntity>> entityFutures = new ArrayList<>(tasks.size() + 1);
            logger.info("开始创建定时任务线程！");

            for (ScheduledEntity scheduledEntity : tasks) {

                ScheduledTask scheduledTask = new ScheduledTask(scheduledEntity, countDownLatch);
                //logger.info(scheduledEntity.getName()+":"+(scheduledEntity.getStartTime().getTime() - calendar.getInstance().getTime().getTime())/1000);
                Future<ScheduledEntity> entityFuture = service.schedule(scheduledTask, (scheduledEntity.getStartTime().getTime() - Calendar.getInstance().getTime().getTime()) / 1000, TimeUnit.SECONDS);
                entityFutures.add(entityFuture);
            }

            countDownLatch.await();

            logger.info("{}个定时任务处理完成，开始处理执行之后的结果！", tasks.size());

            for (Future<ScheduledEntity> future : entityFutures) {
                ScheduledEntity scheduledEntity = future.get();
                logger.info(scheduledEntity.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }

    public static List<ScheduledEntity> getScheduledLists(int listSize) {
        ScheduledEntity scheduledEntity = null;
        List<ScheduledEntity> entities = new ArrayList<>(listSize + 1);
        for (int i = 0; i < listSize; i++) {
            scheduledEntity = new ScheduledEntity();
            scheduledEntity.setTelephone(String.valueOf(System.currentTimeMillis()));
            scheduledEntity.setName("大军" + (i + 1));
            scheduledEntity.setAddress("广东广州" + (i + 1));
            scheduledEntity.setStartTime(getDate(i + 10));
            entities.add(scheduledEntity);
        }
        return entities;
    }

    private static Date getDate(int increment) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, increment);
        return calendar.getTime();
    }

    private static int getInitCoreThreadNum(int listSize) {
        if (listSize > 10000 && listSize <= 100000) {
            initCoreThreadNum = initCoreThreadNum * 2;
        }
        if (listSize > 100000 && listSize <= 500000) {
            initCoreThreadNum = initCoreThreadNum * 4;
        }
        if (listSize > 500000) {
            initCoreThreadNum = initCoreThreadNum * 5;
        }
        return initCoreThreadNum;
    }
}
