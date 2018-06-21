package com.jt.app.repository;


import com.jt.app.model.LotteryUser;
import com.jt.app.model.Tenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LotteryUserRepository extends JpaRepository<LotteryUser, Integer> {
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

    List<LotteryUser> getByTenementId(Integer tenementId);

    LotteryUser getById(Integer id);

    @Modifying
    @Query(value = "update t_lotteryUser u set u.lotteryStatus=(select t.lotteryStatus from t_lotteryUserTemp t where u.id=t.id), u.winLotteryStatus=(select t.winLotteryStatus from t_lotteryUserTemp t where u.id=t.id) where exists (select 1 from t_lotteryUserTemp t where u.id=t.id)", nativeQuery = true)
    void updateLotteryUserStatus();
}
