package com.jt.app.service.mongodb;

import com.jt.app.model.mongodb.Order;
import com.jt.app.redis.GlobalCacheKey;
import com.jt.app.redis.service.RedisService;
import com.jt.app.repository.mongodb.OrderRepository;
import com.jt.app.util.PageUtil;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.collections.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.print.attribute.standard.Destination;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderServiceV1 {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceV1.class);

    @Value("${spring.redis.topicName}")
    private String topicName;

    @Autowired
    private OrderRepository orderRepository;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RedisService redisService;
    @javax.annotation.Resource
    private StringRedisTemplate msgTemplate;
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public OrderRepository getOrderRepository() {
        return this.orderRepository;
    }

    @Transactional
    public Integer updateOrder(Order order) {
        return 0;
    }

    /**
     * 分页与条件查询订单
     *
     * @param pageUtil
     * @param order
     * @return
     */
    public Page<Order> getOrders(PageUtil pageUtil, Order order) throws Exception {
        Page<Order> orders = null;
        if (pageUtil != null && order != null) {
            if (pageUtil.getPageNumber() == 0 && pageUtil.getPageSize() == 0) {
                throw new Exception("数据太多，请使用分页，请传入分页参数[pageNumber、pageSize]!");
            }
            //这里进行添加过滤查询字段
            orders = orderRepository.findAll(pageUtil);
        }
        return orders;
    }

    /**
     * 添加订单
     * 1：放入缓存
     * 2：投递消息key给db代码进行入库数据
     *
     * @param orders
     * @return
     */
    public Integer addOrders(Iterable<Order> orders) {
        long startTime, endTime = 0;
        try {
            redisService.hdel(GlobalCacheKey.order_batch_key, GlobalCacheKey.latest_order_key);
            redisService.hset(GlobalCacheKey.order_batch_key, GlobalCacheKey.latest_order_key, orders, 100);
            //MsgFormat msg = new MsgFormat().converMessageFormat(orders, MsgType.SYNCDB);  //redis消息只支持字符串类型的消息
            msgTemplate.convertAndSend(topicName, GlobalCacheKey.order_batch_key + "-" + GlobalCacheKey.latest_order_key);
            logger.info("异步发送下单信息成功！");

            //查询缓存中订单数量,看用什么方法获取总数
            startTime = System.currentTimeMillis();
            Iterable<Order> list = (Iterable<Order>) redisService.hget(GlobalCacheKey.order_batch_key, GlobalCacheKey.latest_order_key);
            endTime = System.currentTimeMillis();
            List<Order> orderList = IteratorUtils.toList(list.iterator());
            logger.info("从缓存中获取信息【" + orderList.size() + "】条成功！共耗时：【" + (endTime - startTime) / 1000 + "】秒！");
            return orderList.size();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("异步发送下单信息失败！");
            return 0;
        }
    }

    public boolean deleteAll() {
        try {
            orderRepository.deleteAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendMsg(String msg) {
        try {
            //存入缓存
            logger.info("发送消息：" + msg);
            msgTemplate.convertAndSend(topicName, msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //http://www.leftso.com/blog/94.html
    public void addOrdersForAMQ(Iterable<Order> orders){
        //向amq队列发消息
        //Destination destination = new ActiveMQQueue("TEST_QUEUE_LOG");// 这里定义了Queue的key
        //this.jmsMessagingTemplate.convertAndSend(destination, message);
        orderRepository.save(orders);
    }
}
