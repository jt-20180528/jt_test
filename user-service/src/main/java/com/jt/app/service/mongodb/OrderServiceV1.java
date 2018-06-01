package com.jt.app.service.mongodb;

import com.jt.app.model.mongodb.Order;
import com.jt.app.repository.mongodb.OrderRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderServiceV1 {

    @Autowired
    private OrderRepository orderRepository;

    public OrderRepository getOrderRepository(){
        return this.orderRepository;
    }

    @Transactional
    public Integer updateOrder(Order order){
        return 0;
    }

}
