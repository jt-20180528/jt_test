package com.jt.app.repository;


import com.jt.app.model.UserOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface UserOrganizationRepository extends JpaRepository<UserOrganization, Integer> {
    //自定义方法

    /*@Query(value = "SELECT c.*\n" +
            "FROM (\n" +
            "       SELECT *\n" +
            "       FROM cart_event\n" +
            "       WHERE user_id = ?1 AND (cart_event_type = 3 OR cart_event_type = 2)\n" +
            "       ORDER BY cart_event.created_at DESC\n" +
            "       LIMIT 1\n" +
            "     ) t\n" +
            "  RIGHT JOIN cart_event c ON c.user_id = t.user_id\n" +
            "WHERE c.created_at BETWEEN coalesce(t.created_at, 0) AND 9223372036854775807 AND coalesce(t.id, -1) != c.id\n" +
            "ORDER BY c.created_at ASC", nativeQuery = true)
    Channel getCartEventStreamByUser(Long userId);*/

    @Modifying
    @Query("update UserOrganization uo set uo.createTime=?1, uo.updateTime=?2 where uo.id=?3")
    Integer updateUserOrganization(Date create_time, Date update_time, Integer id);

    UserOrganization getById(Integer id);
}
