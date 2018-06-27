package com.jt.app.service;

import com.jt.app.model.op.model.BetOrderTemp;
import com.jt.app.repository.BetOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class BetOrderServiceV1 {

    @Autowired
    private BetOrderRepository betOrderRepository;

    @PersistenceContext
    protected EntityManager em;

    public BetOrderRepository getBetOrderRepository() {
        return this.betOrderRepository;
    }

    public Integer getRecodeCountBetOrder() {
        return betOrderRepository.getRecodeCountBetOrder();
    }

    public Integer getRecodeCountBetOrderTemp() {
        return betOrderRepository.getRecodeCountBetOrderTemp();
    }

    /**
     * 拼接sql添加租户
     *
     * @param jointSql
     */
    @Transactional
    public boolean jointAddBetOrder(String jointSql) {
        try {
            Query query = em.createNativeQuery(jointSql);
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 刪除表数据
     *
     * @param tableName
     */
    @Transactional
    public boolean deleteAllBySql(String tableName) {
        try {
            Query query = em.createNativeQuery("TRUNCATE table " + tableName + "");
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Integer getRecodeCountBySql(String tableName) {
        try {
            Query query = em.createNativeQuery("select count(1) from " + tableName + "");
            return (Integer) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Transactional
    public boolean updateBetOrderStatusByExist() {
        try {
            betOrderRepository.updateBetOrderStatusByExist();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public boolean updateBetOrderStatusByJpaSql(Integer lotteryNumberId) {
        try {
            betOrderRepository.updateBetOrderStatusByJpaSql(lotteryNumberId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public boolean updateBetOrderStatusByJdbcSql(Integer lotteryNumberId) {
        try {
            String sql = "UPDATE t_bet_order b INNER JOIN t_bet_order_temp td ON b.id = td.id and td.LOTTERY_NUMBER_ID=" + lotteryNumberId + " " +
                    "set b.WIN_STATUS=td.win_status," +
                    "b.LOTTERY_NUMBER=td.LOTTERY_NUMBER," +
                    "b.WIN_SECRET=td.win_secret," +
                    "b.odds=td.odds," +
                    "b.WIN_AMOUNT=td.win_amount," +
                    "b.WIN_COUNT=td.win_count";
            Query query = em.createNativeQuery(sql);
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Object> getBetOrderTempsByLNId(Integer lotteryNumberId){
        return betOrderRepository.getBetOrderTempsByLNId(lotteryNumberId);
    }
}
