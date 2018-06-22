package com.jt.app.repository;


import com.jt.app.model.Tenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TenementRepository extends JpaRepository<Tenement, Integer> {
    //自定义方法

    /*@Query(value = "SELECT c.*\n" +
            "FROM (\n" +
            "       SELECT *\n" +
            "       FROM cart_event\n" +
            "       WHERE Job_id = ?1 AND (cart_event_type = 3 OR cart_event_type = 2)\n" +
            "       ORDER BY cart_event.created_at DESC\n" +
            "       LIMIT 1\n" +
            "     ) t\n" +
            "  RIGHT JOIN cart_event c ON c.Job_id = t.Job_id\n" +
            "WHERE c.created_at BETWEEN coalesce(t.created_at, 0) AND 9223372036854775807 AND coalesce(t.id, -1) != c.id\n" +
            "ORDER BY c.created_at ASC", nativeQuery = true)
    Channel getCartEventStreamByJob(Long JobId);*/


    Tenement getById(Integer id);

    List<Tenement> getByLotteryStatus(Integer lotteryStatus);

    @Query(value="select * from t_tenement",nativeQuery = true)
    List<Tenement> getTenementBySql();
}
