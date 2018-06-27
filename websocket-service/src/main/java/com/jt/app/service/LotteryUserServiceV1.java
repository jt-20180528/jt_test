package com.jt.app.service;

import com.jt.app.model.LotteryUser;
import com.jt.app.model.Tenement;
import com.jt.app.repository.LotteryUserRepository;
import com.jt.app.repository.TenementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class LotteryUserServiceV1 {

    @Autowired
    private LotteryUserRepository lotteryUserRepository;

    @PersistenceContext
    protected EntityManager em;

    /**
     * 获取租户
     *
     * @param id
     * @return
     */
    public LotteryUser getTenementById(Integer id) {
        return lotteryUserRepository.getOne(id);
    }

    public LotteryUserRepository getTenementRepository() {
        return this.lotteryUserRepository;
    }

    /**
     * 添加彩票用户
     *
     * @param lotteryUser
     */
    public LotteryUser addTenement(LotteryUser lotteryUser) {
        return lotteryUserRepository.save(lotteryUser);
    }

    public List<LotteryUser> getLotteryUsersByTenementId(Integer tenementId){
        return lotteryUserRepository.getByTenementId(tenementId);
    }

    @Transactional
    public boolean updateLotteryUserStatus(){
        try{
            lotteryUserRepository.updateLotteryUserStatus();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 拼接sql添加彩票用户
     *
     * @param jointSql
     */
    @Transactional
    public boolean jointAddLotteryUser(String jointSql) {
        try {
            Query query = em.createNativeQuery(jointSql);
            query.executeUpdate();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
