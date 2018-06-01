package com.jt.app.repository.mongodb;

import com.jt.app.model.mongodb.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, Integer> {
    //自定义方法

    /*@Modifying
    @Query(value="update Order set ",nativeQuery = true)
    Integer updateOrder();*/
}
