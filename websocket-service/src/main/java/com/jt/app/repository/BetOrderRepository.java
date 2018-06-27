package com.jt.app.repository;


import com.jt.app.model.op.model.BetOrder;
import com.jt.app.model.op.model.BetOrderTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BetOrderRepository extends JpaRepository<BetOrder, Integer> {
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

    @Modifying
    @Query(value = "UPDATE t_bet_order t1 set " +
            "t1.WIN_STATUS=(select WIN_STATUS from t_bet_order_temp t2 where t1.id=t2.id)," +
            "t1.LOTTERY_NUMBER=(select LOTTERY_NUMBER from t_bet_order_temp t2 where t1.id=t2.id)," +
            "t1.odds=(select odds from t_bet_order_temp t2 where t1.id=t2.id)," +
            "t1.WIN_AMOUNT=(select WIN_AMOUNT from t_bet_order_temp t2 where t1.id=t2.id)," +
            "t1.WIN_COUNT=(select WIN_COUNT from t_bet_order_temp t2 where t1.id=t2.id)," +
            "t1.WIN_SECRET=(select WIN_SECRET from t_bet_order_temp t2 where t1.id=t2.id)" +
            "where exists (select 1 from t_bet_order_temp t2 where t1.id=t2.id)", nativeQuery = true)
    void updateBetOrderStatusByExist();

    @Modifying
    @Query(value = "UPDATE t_bet_order b INNER JOIN t_bet_order_temp td ON b.id = td.id and td.LOTTERY_NUMBER_ID=?1 " +
            "set b.WIN_STATUS=td.win_status," +
            "b.LOTTERY_NUMBER=td.LOTTERY_NUMBER," +
            "b.WIN_SECRET=td.win_secret," +
            "b.odds=td.odds," +
            "b.WIN_AMOUNT=td.win_amount," +
            "b.WIN_COUNT=td.win_count", nativeQuery = true)
    void updateBetOrderStatusByJpaSql(Integer lotteryNumberId);

    @Query(value ="select count(1) from t_bet_order", nativeQuery = true)
    Integer getRecodeCountBetOrder();

    @Query(value ="select count(1) from t_bet_order_temp", nativeQuery = true)
    Integer getRecodeCountBetOrderTemp();

    @Query(value ="select * from t_bet_order_temp where LOTTERY_NUMBER_ID=?1", nativeQuery = true)
    List<Object> getBetOrderTempsByLNId(Integer lotteryNumberId);
}
