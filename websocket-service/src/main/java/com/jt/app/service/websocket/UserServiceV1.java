package com.jt.app.service.websocket;

import com.jt.app.jpa.util.BatchService;
import com.jt.app.model.User;
import com.jt.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceV1 implements BatchService<User> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;
    @PersistenceContext
    protected EntityManager em;

    /**
     * 获取用户
     *
     * @param id
     * @return
     */
    public User getUserById(Integer id) {
        return userRepository.getOne(id);
    }

    public UserRepository getUserRepository() {
        return this.userRepository;
    }

    /**
     * 添加用户
     *
     * @param user
     */
    public void addUser(User user) {
        userRepository.save(user);
    }

    /**
     * 更新用户
     *
     * @param create_Time
     * @param update_time
     * @param id
     * @return
     */
    @Transactional
    public Integer updateUser(Date create_Time, Date update_time, Integer id) {
        return userRepository.updateUser(create_Time, update_time, id);
    }

    /**
     * 查找用户
     *
     * @param name
     * @return
     */
    public User getUserByName(String name) {
        return userRepository.findUser(name);
    }

    /**
     * 根据账号密码获取用户
     *
     * @param loginName
     * @param loginPasswd
     * @return
     */
    public User getUserByNamePasswd(String loginName, String loginPasswd) {
        return userRepository.getUserByNamePasswd(loginName, loginPasswd);
    }

    /**
     * 根据账号获取用户
     *
     * @param loginName
     * @return
     */
    public User getByLoginName(String loginName) {
        return userRepository.getByLoginName(loginName);
    }

    /**
     * 模糊查询用户名
     *
     * @param userName
     * @return
     */
    public Integer getCountByNameLike(String userName) {
        return userRepository.getCountByNameLike(userName);
    }

    /**
     * 模糊刪除
     *
     * @param userName
     * @return
     */
    @Transactional
    public Integer deleteAllByNameLike(String userName) {
        return userRepository.deleteAllByNameLike(userName);
    }

    /**
     * 批量新增
     *
     * @param list
     * @param batchSize 批次数量
     * @return
     */
    @Override
    @Transactional
    public Integer batchInsert(List<User> list, Integer batchSize) {
        try {
            for (int i = 0; i < list.size(); i++) {
                em.persist(list.get(i));
                if (i % batchSize == 0) {
                    em.flush();
                    em.clear();
                    logger.info("第【" + i / batchSize + "】批数据添加完毕！");
                }
            }
            return 1;
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * 批量修改
     *
     * @param list
     * @param batchSize 批次数列
     * @return
     */
    @Override
    @Transactional
    public Integer batchUpdate(List<User> list, Integer batchSize) {
        try {
            for (int i = 0; i < list.size(); i++) {
                em.merge(list.get(i));
                if (i % batchSize == 0) {
                    em.flush();
                    em.clear();
                    logger.info("第【" + i / batchSize + "】批数据修改完毕！");
                }
            }
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 多value新增
     *
     * @param values
     * @return
     */
    @Transactional
    public Integer multiValueInsert(String tableName, String values) {
       try {
            Query query = em.createNativeQuery(values);
            query.executeUpdate();
            return 1;
        }catch(Exception e){
           e.printStackTrace();
            return 0;
        }
        //使用这种方式不行，一直有问题
        /*try {
            final String tableHeader = "insert into " + tableName + " (userName,address,createTime,updateTime,createUser,updateUser,email,lastLoginAddr,loginName,loginPasswd) values ";
            values = values.replace(tableHeader, "");
            userRepository.multiValueInsert(tableName, values);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }*/
    }

    public Integer getTempCount() {
        return userRepository.getTempCount();
    }

    @Transactional
    public Integer syncTemp2User() {
        return userRepository.syncTemp2User();
    }

    @Transactional
    public Integer deleteAllTemp() {
        return userRepository.deleteAllTemp();
    }
}
