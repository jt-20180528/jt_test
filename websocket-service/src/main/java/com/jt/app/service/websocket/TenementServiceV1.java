package com.jt.app.service.websocket;

import com.jt.app.model.Tenement;
import com.jt.app.repository.TenementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class TenementServiceV1 {

    @Autowired
    private TenementRepository tenementRepository;

    @PersistenceContext
    protected EntityManager em;

    /**
     * 获取租户
     *
     * @param id
     * @return
     */
    public Tenement getTenementById(Integer id) {
        return tenementRepository.getOne(id);
    }

    public TenementRepository getTenementRepository() {
        return this.tenementRepository;
    }

    /**
     * 添加租户
     *
     * @param tenement
     */
    public Tenement addTenement(Tenement tenement) {
        return tenementRepository.save(tenement);
    }

    public List<Tenement> getNoSendAwardTenement(Integer lotteryStatus){
        return tenementRepository.getByLotteryStatus(lotteryStatus);
    }

    /**
     * 拼接sql添加租户
     *
     * @param jointSql
     */
    @Transactional
    public boolean jointAddTenement(String jointSql) {
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
