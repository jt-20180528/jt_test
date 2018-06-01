package com.jt.app.repository;


import com.jt.app.model.User;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
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

    @Query("from User u where u.userName=:userName")
    User findUser(@Param("userName") String userName);

    @Modifying
    @Query("update User u set u.createTime=?1, u.updateTime=?2 where u.id=?3")
    Integer updateUser(Date create_time, Date update_time, Integer id);

    @Query("from User u where u.loginName=?1 and u.loginPasswd=?2")
    User getUserByNamePasswd(String loginName, String loginPasswd);

    User getByLoginNameAndLoginPasswd(String loginName, String loginPasswd);

    User getById(Integer id);

    User getByLoginName(String loginName);

    @Query("select count(u.id) from User u where u.userName like concat('%',?1,'%')")
    Integer getCountByNameLike(String userName);

    @Modifying
    @Query("delete from User u where u.userName like concat('%',?1,'%')")
    Integer deleteAllByNameLike(String userName);

    //这种方式不行，一直有问题
    @Modifying
    @Query(value = "insert into ?1 (userName,address,createTime,updateTime,createUser,updateUser,email,lastLoginAddr,loginName,loginPasswd) values ?2", nativeQuery = true)
    void multiValueInsert(String tableName,String values);

    @Query(value = "select count(u.id) from t_user_temp u", nativeQuery = true)
    Integer getTempCount();

    @Modifying
    @Query(value = "update t_user u set u.createTime=(select t.createTime from t_user_temp t where u.id=t.id), u.updateTime=(select t.updateTime from t_user_temp t where u.id=t.id), u.userName=(select t.userName from t_user_temp t where u.id=t.id), u.lastLoginAddr=(select t.lastLoginAddr from t_user_temp t where u.id=t.id) where exists (select 1 from t_user_temp t where u.id=t.id)", nativeQuery = true)
    Integer syncTemp2User();

    @Modifying
    @Query(value = "delete from t_user_temp", nativeQuery = true)
    Integer deleteAllTemp();
}
