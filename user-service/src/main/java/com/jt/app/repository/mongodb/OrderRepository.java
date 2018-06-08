package com.jt.app.repository.mongodb;

import com.jt.app.model.mongodb.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.transaction.Transactional;

public interface OrderRepository extends MongoRepository<Order, Integer> {
    //自定义方法

    /*@Modifying
    @Query(value="update Order set ",nativeQuery = true)
    Integer updateOrder();*/

    Order getByOrderCode(String orderCode);

    Order findByOrderCode(String orderCode);

    @Transactional
    void deleteByOrderCode(String orderCode);
}
