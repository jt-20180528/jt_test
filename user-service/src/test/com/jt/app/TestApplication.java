package com.jt.app;

import com.jt.app.model.*;
import com.jt.app.model.mongodb.Order;
import com.jt.app.redis.service.RedisService;
import com.jt.app.service.*;
import com.jt.app.service.mongodb.OrderServiceV1;
import com.jt.app.util.EncryptUtil;
import com.jt.app.util.TimeUtil;
import org.apache.http.util.Asserts;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {UserApplication.class})
@ActiveProfiles(profiles = "test")
public class TestApplication {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserServiceV1 userServiceV1;
    @Autowired
    private RoleServiceV1 roleServiceV1;
    @Autowired
    private UserRoleServiceV1 userRoleServiceV1;
    @Autowired
    private ResourceServiceV1 resourceServiceV1;
    @Autowired
    private RoleResourceServiceV1 roleResourceServiceV1;
    @Autowired
    private OrderServiceV1 orderServiceV1;
    @javax.annotation.Resource
    private StringRedisTemplate msgTemplate;
    @Autowired
    private RedisService redisService;
    @Value("${spring.redis.topicName}")
    private String topicName;

    @Test
    public void addUserData() {
        long num = userServiceV1.getUserRepository().count();
        if (num > 0) {
            logger.info("当前测试表中数据：" + num + "条，无需增加！");
        } else {
            final Integer addNum = 100;
            final String nameFlag = "-t1-";
            final Integer batchSize = 10;
            long startTime, endTime = 0;
            List<User> users = this.globalBatchUser(addNum, nameFlag);
            startTime = System.currentTimeMillis();
            Integer reslut = userServiceV1.batchInsert(users, batchSize);
            endTime = System.currentTimeMillis();
            if (reslut == 1) {
                logger.info("批量插入【" + batchSize + "】成功！");
            } else {
                logger.error("批量插入【" + batchSize + "】失败！");
            }
            logger.info("添加" + addNum + "记录花费时间：【" + (endTime - startTime) / 1000 + "】秒");
        }
    }

    @Test
    public void getUserByName() {
        long num = userServiceV1.getUserRepository().count();
        if (num == 0) {
            addUserData();
        }
        List<User> userList = userServiceV1.getUserRepository().findAll();
        int randomId = cycleRandom(userList.size());
        final String name = "admin" + randomId;
        User user = userServiceV1.getUserByName(name);
        if (user == null) {
            logger.error("获取用户失败！");
        }
    }

    @Test
    public void delRandomUser() {
        long num = userServiceV1.getUserRepository().count();
        if (num == 0) {
            addUserData();
        }
        List<User> userList = userServiceV1.getUserRepository().findAll();
        int randomId = cycleRandom(userList.size());
        User user = null;
        try {
            user = userServiceV1.getUserRepository().findOne(randomId);
            if (user != null) {
                userServiceV1.getUserRepository().delete(user);
                logger.info("删除[" + user.getUserName() + "]成功！");
            }
        } catch (Exception e) {
            logger.error("删除[" + user.getUserName() + "]失败！", e);
        }
    }

    @Test
    public void modifyUser() {
        long num = userServiceV1.getUserRepository().count();
        if (num == 0) {
            addUserData();
        }
        List<User> userList = userServiceV1.getUserRepository().findAll();
        int randomId = cycleRandom(userList.size());
        User oldUser = userServiceV1.getUserRepository().findOne(randomId);
        Integer flag = userServiceV1.updateUser(TimeUtil.ymdHms2date(), TimeUtil.ymdHms2date(), oldUser.getId
                ());
        if (flag == 1) {
            User newUser = userServiceV1.getUserRepository().findOne(randomId);
            logger.info("修改" + oldUser.getUserName() + "成功！修改前：" + oldUser.toString() + "|| 修改后：" + newUser
                    .toString());
        }
    }

    @Test
    public void getUserByUserId() {
        long num = userServiceV1.getUserRepository().count();
        if (num == 0) {
            addUserData();
        }
        List<User> userList = userServiceV1.getUserRepository().findAll();
        int randomId = cycleRandom(userList.size());
        User user = userServiceV1.getUserRepository().findOne(randomId);
        logger.info("获取到随机用户：" + user.toString());
    }

    public int cycleRandom(int initNum) {
        int randomId = new Random().nextInt(initNum) + 1;
        logger.info("随机数为：" + randomId);
        boolean existId = userServiceV1.getUserRepository().exists(randomId);
        if (existId) {
            return randomId;
        } else {
            return cycleRandom(initNum);
        }
    }

    /**
     * 测试添加认证数据
     */
    @Test
    public void testAddAuthData() {

        final String loginName = "root1";

        //添加角色
        addRoleData(loginName);

        //添加资源权限
        addResourceData(loginName);
    }

    public void addRoleData(String loginName) {
        Long roleCount = roleServiceV1.getRoleCount();
        if (roleCount == 0) {
            final Integer num = 10;
            Role role = null;
            for (int i = 0; i < num; i++) {
                role = new Role();
                role.setRoleName("角色" + i + 1);
                role.setCreateTime(TimeUtil.ymdHms2date());
                role.setUpdateTime(TimeUtil.ymdHms2date());
                role.setCreateUser("lj");
                role.setUpdateUser("lj");
                roleServiceV1.getRoleRepository().save(role);
            }
            logger.info("认证数据多余" + roleCount + "条，已完成！");
            roleCount = 10L;
        } else {
            logger.info("认证数据多余1条，无需添加！");
        }
        //取随机个数作为角色id
        List<Integer> roleIds = new ArrayList<>();
        for (int i = 0; i < (roleCount > 2 ? roleCount - 1 : roleCount); i++) {
            int randomId = new Random().nextInt(roleCount.intValue()) + 1;
            roleIds.add(randomId);
            logger.info("随机角色编号为：【" + randomId + "】重复的会被剔除");
        }
        HashSet<Integer> hashSet = new HashSet<Integer>(roleIds);
        roleIds.clear();
        roleIds.addAll(hashSet);

        //把随机角色给予用户root1
        Integer root1Id = userServiceV1.getByLoginName(loginName).getId();
        UserRole userRole = null;
        for (Integer roleId : roleIds) {
            userRole = new UserRole();
            userRole.setUserId(root1Id);
            userRole.setRoleId(roleId);
            userRole.setCreateUser("lj");
            userRole.setUpdateUser("lj");
            userRole.setCreateTime(TimeUtil.ymdHms2date());
            userRole.setUpdateTime(TimeUtil.ymdHms2date());
            userRoleServiceV1.addUserRole(userRole);
        }
    }

    public void addResourceData(String loginName) {
        Long resourceCount = resourceServiceV1.getResourceCount();
        if (resourceCount == 0) {
            //添加资源数据
            Resource resource = null;
            final Integer num = 10;
            for (int i = 0; i < num; i++) {
                resource = new Resource();
                resource.setResourceName("药品管理" + i);
                resource.setResourceUrl("http://localhost:8086/drag_manager");
                resource.setParentId(i == 0 ? 0 : i % 2 == 0 ? 1 : 2);
                resource.setCreateTime(TimeUtil.ymdHms2date());
                resource.setUpdateTime(TimeUtil.ymdHms2date());
                resource.setCreateUser("lj");
                resource.setUpdateUser("lj");
                resourceServiceV1.addResource(resource);
            }
            logger.info("认证数据多余10条，已完成！");
            resourceCount = 10L;
        } else {
            logger.info("认证数据多余1条，无需添加！");
        }

        //把随机资源给予给予角色root1其中一个角色
        Integer root1Id = userServiceV1.getByLoginName(loginName).getId();
        List<Integer> roleIds = userRoleServiceV1.getUserRoleRepository().getRoleIdsByUserId(root1Id);
        if (roleIds == null || roleIds.size() == 0) {
            Assert.fail("root1没有任何角色，现在添加角色");
        } else {
            Integer roleId = roleIds.get(0);
            logger.info("给root1用户中角色编号为：【" + roleIds + "】添加资源！");

            //取随机个数作为资源id
            List<Integer> resourceIds = new ArrayList<>();
            for (int i = 0; i < (resourceCount > 2 ? resourceCount - 1 : resourceCount); i++) {
                int randomId = new Random().nextInt(resourceCount.intValue()) + 1;
                resourceIds.add(randomId);
                logger.info("随机资源编号为：【" + randomId + "】重复的会被剔除");
            }
            HashSet<Integer> hashSet = new HashSet<Integer>(resourceIds);
            resourceIds.clear();
            resourceIds.addAll(hashSet);

            //给用户root1取出的角色添加随机资源
            RoleResource roleResource = null;
            for (Integer resourceId : resourceIds) {
                roleResource = new RoleResource();
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(resourceId);
                roleResource.setCreateTime(TimeUtil.ymdHms2date());
                roleResource.setUpdateTime(TimeUtil.ymdHms2date());
                roleResource.setUpdateUser("lj");
                roleResource.setCreateUser("lj");
                roleResourceServiceV1.getRoleResourceRepository().save(roleResource);
            }
        }
    }

    @Test
    public void testBatchAdd1WTime() {
        //首先刪除已有t2測試
        final String nameFlag = "-t2-";
        long startTime, endTime = 0;
        final Integer addNum = 100000;
        final Integer batchSize = 100000;
        Integer usersNum = userServiceV1.getCountByNameLike(nameFlag);
        if (usersNum > 0) {
            startTime = System.currentTimeMillis();
            Integer delNum = userServiceV1.deleteAllByNameLike(nameFlag);
            endTime = System.currentTimeMillis();
            if (delNum > 0) {
                logger.info("刪除t2测试数据一共：【" + usersNum + "】条，用时：【" + (endTime - startTime) / 1000 + "】秒");
            }
        }
        List<User> users = this.globalBatchUser(addNum, nameFlag);
        startTime = System.currentTimeMillis();
        Integer reslut = userServiceV1.batchInsert(users, batchSize);
        endTime = System.currentTimeMillis();
        if (reslut == 1) {
            logger.info("批量插入【" + batchSize + "】成功！");
        } else {
            logger.error("批量插入【" + batchSize + "】失败！");
        }
        logger.info("添加" + addNum + "条记录花费时间：【" + (endTime - startTime) / 1000 + "】秒");
    }

    @Test
    public void testBatchUpdate1WTime() {
        final String nameFlag = "-t2-";
        long startTime, endTime = 0;
        final Integer addNum = 100000;
        final Integer batchSize = 100000;
        Integer usersNum = userServiceV1.getCountByNameLike(nameFlag);
        if (usersNum == 0) {
            this.testBatchAdd1WTime();
        }

        //修改数据
        List<User> users = this.globalBatchUser(addNum, nameFlag);
        startTime = System.currentTimeMillis();
        Integer reslut = userServiceV1.batchUpdate(users, batchSize);
        endTime = System.currentTimeMillis();
        if (reslut == 1) {
            logger.info("批量修改【" + batchSize + "】成功！");
        } else {
            logger.error("批量修改【" + batchSize + "】失败！");
        }
        logger.info("修改" + addNum + "条记录花费时间：【" + (endTime - startTime) / 1000 + "】秒");
    }

    public List<User> globalBatchUser(Integer addNum, String flag) {
        List<User> users = new ArrayList<User>(addNum + 1);
        User user = null;
        for (int i = 0; i < addNum; i++) {
            user = new User();
            user.setUserName("用户" + flag + i);
            user.setAddress("广东-广州");
            user.setCreateTime(TimeUtil.ymdHms2date());
            user.setUpdateTime(TimeUtil.ymdHms2date());
            user.setCreateUser("lj" + i);
            user.setEmail("1245282613@qq.com");
            user.setLastLoginAddr("广东-广州");
            user.setLoginName("root" + flag + i);
            user.setLoginPasswd(EncryptUtil.md5Encode("root" + i));
            //userServiceV1.addUser(user);
            users.add(user);
        }
        return users;
    }

    public String jointInsertSql(Integer addNum, String flag, String tableName) {
        StringBuilder sb = new StringBuilder();
        final String tableHeader = "insert into " + tableName + " (userName,address,createTime,updateTime,createUser,updateUser,email,lastLoginAddr,loginName,loginPasswd) values ";
        sb.append(tableHeader);
        for (int i = 0; i < addNum; i++) {
            /*if (i % batchSize == 0) {
                if (sb.toString().length() > 0 && sb.toString().lastIndexOf(",") > 1) {
                    sb.replace(sb.lastIndexOf(","), sb.lastIndexOf(",") + 1, ";");
                }
                sb.append(tableHeader);
            }*/
            sb.append("(");
            sb.append("'用户" + flag + i + "'" + ",");
            sb.append("'广东-广州'" + ",");
            sb.append("'" + TimeUtil.ymdhms2str() + "'" + ",");
            sb.append("'" + TimeUtil.ymdhms2str() + "'" + ",");
            sb.append("'lj" + i + "'" + ",");
            sb.append("'1245282613@qq.com'" + ",");
            sb.append("'广东-广州'" + ",");
            sb.append("'root" + flag + i + "'" + ",");
            sb.append("'root" + flag + i + "'" + ",");
            sb.append("'" + EncryptUtil.md5Encode("root" + i) + "'");
            sb.append("),");
        }
        //return sb.toString().endsWith(",") ? sb.substring(0, sb.toString().length() - 1) + ";" : sb.toString();
        return sb.substring(0, sb.toString().length() - 1);
    }

    @Test
    public void testMultiValueInsert() {
        logger.info("添加t_user表数据！");
        //首先刪除已有t2測試
        final String nameFlag = "-t2-";
        long startTime, endTime = 0;
        final Integer addNum = 1;
        final String tableName = "t_user";
        Integer usersNum = userServiceV1.getCountByNameLike(nameFlag);
        if (usersNum > 0) {
            startTime = System.currentTimeMillis();
            Integer delNum = userServiceV1.deleteAllByNameLike(nameFlag);
            endTime = System.currentTimeMillis();
            if (delNum > 0) {
                logger.info("刪除t2测试数据一共：【" + usersNum + "】条，用时：【" + (endTime - startTime) / 1000 + "】秒");
            }
        }
        startTime = System.currentTimeMillis();
        String usersSql = this.jointInsertSql(addNum, nameFlag, tableName);
        //usersSql = "insert into t_user(userName,address,createTime,updateTime,createUser,updateUser,email,lastLoginAddr,loginName,loginPasswd) values ('用户-t2-0','广东-广州','2018-05-31 02:27:49','2018-05-31 02:27:49','lj0','1245282613@qq.com','广东-广州','root-t2-0','root-t2-0','cb300cf0d7943bb87225d9a105ee9636'),('用户-t2-1','广东-广州','2018-05-31 05:47:32','2018-05-31 05:47:32','lj1','1245282613@qq.com','广东-广州','root-t2-1','root-t2-1','e5d9dee0892c9f474a174d3bfffb7810'),('用户-t2-3','广东-广州','2018-05-31 05:47:32','2018-05-31 05:47:32','lj3','1245282613@qq.com','广东-广州','root-t2-3','root-t2-3','5a63a226e4905bc7885efb9c83464397')";
        endTime = System.currentTimeMillis();
        //logger.info("拼接的sql：" + usersSql);
        logger.info("拼接" + addNum + "条记录花费时间：【" + (endTime - startTime) / 1000 + "】秒");

        startTime = System.currentTimeMillis();
        Integer reslut = userServiceV1.multiValueInsert(tableName, usersSql);
        endTime = System.currentTimeMillis();
        if (reslut == 1) {
            logger.info("多值插入【" + addNum + "】成功！");
        } else {
            logger.error("多值插入【" + addNum + "】失败！");
        }
        logger.info("添加" + addNum + "条记录花费时间：【" + (endTime - startTime) / 1000 + "】秒,当前时间：" + System.currentTimeMillis());
    }

    @Test
    public void testMultiValueInsertTemp() {
        logger.info("添加t_user_temp表数据！");
        final Integer addNum = 600000;
        long startTime, endTime = 0;
        final String nameFlag = "-t3-update-";
        final String tableName = "t_user_temp";
        Integer tempNum = userServiceV1.getTempCount();
        if (tempNum != addNum) {
            startTime = System.currentTimeMillis();
            Integer delFlag = userServiceV1.deleteAllTemp();
            endTime = System.currentTimeMillis();
            if (delFlag > 0) {
                logger.info("刪除临时表：" + tempNum + "条记录成功，花费时间：【" + (endTime - startTime) / 1000 + "】秒");
            } else {
                logger.error("刪除临时表失败！");
            }

            //重新新增数据
            startTime = System.currentTimeMillis();
            String usersSql = this.jointInsertSql(addNum, nameFlag, tableName);
            endTime = System.currentTimeMillis();
            //logger.info("拼接的sql：" + usersSql);
            logger.info("拼接" + addNum + "条记录花费时间：【" + (endTime - startTime) / 1000 + "】秒");

            startTime = System.currentTimeMillis();
            Integer reslut = userServiceV1.multiValueInsert(tableName, usersSql);
            endTime = System.currentTimeMillis();
            if (reslut == 1) {
                logger.info("多值插入【" + addNum + "】成功！");
            } else {
                logger.error("多值插入【" + addNum + "】失败！");
            }
            logger.info("添加" + addNum + "条记录花费时间：【" + (endTime - startTime) / 1000 + "】秒");
        } else {
            logger.info("临时表存在数据【" + tempNum + "】条，无需新增！");
        }
    }

    @Test
    public void testSyncTemp2User() {
        logger.info("开始同步临时表到用户表！");
        long startTime, endTime = 0;
        startTime = System.currentTimeMillis();
        Integer syncNum = userServiceV1.syncTemp2User();
        endTime = System.currentTimeMillis();
        logger.info("同步" + syncNum + "条记录花费时间：【" + (endTime - startTime) / 1000 + "】秒");
    }

    //////////////////////测试mongodb数据库///////////////////////////

    /**
     * 测试mongodb数据库写入定量数据
     * 追加测试下单放入redis然后异步交给mongodb去入db
     */
    @Test
    public void testMonAdd1WTime() {
        final int addNum = 1000000;
        long startTime, endTime = 0;
        Iterable<Order> orders = this.buildOrderList(addNum);
        //新增数据前，先刪除所有数据
        long recodeNum = orderServiceV1.getOrderRepository().count();
        if (recodeNum > 0) {
            startTime = System.currentTimeMillis();
            boolean delFlag = orderServiceV1.deleteAll();
            endTime = System.currentTimeMillis();
            logger.info("刪除mongodb数据【" + recodeNum + "】条" + (delFlag ? "成功！耗时【" + (endTime - startTime) / 1000 + "】秒" : "失败！"));
        }
        //次出修改，直接调用服务的新增方法
        startTime = System.currentTimeMillis();
        Integer orderNum = orderServiceV1.addOrders(orders);
        endTime = System.currentTimeMillis();
        //Asserts.check(order_res.size() == addNum, "入库数据返回同预设不一致！");
        //logger.info("入库" + order_res.size() + "条记录成功，花费时间：【" + (endTime - startTime) / 1000 + "】秒");
        Asserts.check(addNum == orderNum, "入库数据失败,数量不一致！");
        logger.info("入库" + addNum + "条记录成功，花费时间：【" + (endTime - startTime) / 1000 + "】秒");
    }

    public String buildOrderCode() {
        Long ranDom = new Random(1000000 + 1).nextLong();
        String currentTime = TimeUtil.ymdHmsNoFormat2str();
        return currentTime + "-" + ranDom;
    }

    public Iterable<Order> buildOrderList(Integer addNum) {
        List<Order> orders = new ArrayList<Order>();
        Order order = null;
        for (int i = 0; i < addNum; i++) {
            String orderCode = this.buildOrderCode();
            order = new Order();
            order.setUserId("user-" + i + orderCode);
            order.setStatus(1);
            order.setOrderCode(orderCode);
            order.setCreateTime(TimeUtil.ymdHms2date());
            orders.add(order);
        }
        return (Iterable<Order>) orders;
    }

    /**
     * 测试mongodb数据库更新定量数据
     */
    @Test
    public void testMonUpdate1WTime() {
        final int addNum = 100000;
        long startTime, endTime = 0;
        startTime = System.currentTimeMillis();
        List<Order> orders = orderServiceV1.getOrderRepository().findAll();
        endTime = System.currentTimeMillis();
        logger.info("查询数据" + orders.size() + "条记录成功，花费时间：【" + (endTime - startTime) / 1000 + "】秒");
        for (int i = 0; i < orders.size(); i++) {
            orders.get(i).setCreateTime(TimeUtil.ymdHms2date());
            orders.get(i).setOrderCode(this.buildOrderCode());
            orders.get(i).setStatus(0);
            orders.get(i).setUserId(orders.get(i).getUserId() + "-update");
        }
        startTime = System.currentTimeMillis();
        List<Order> order_res = orderServiceV1.getOrderRepository().save(orders);
        endTime = System.currentTimeMillis();
        Asserts.check(order_res.size() == addNum, "入库数据返回同预设不一致！");
        logger.info("同步更新" + order_res.size() + "条记录成功，花费时间：【" + (endTime - startTime) / 1000 + "】秒");
    }

    //添加测试账号
    @Test
    public void testAddTestAccount() {
        final String loginName = "admin";
        final String loginPass = EncryptUtil.md5Encode("admin");
        User user = userServiceV1.getUserRepository().getByLoginName(loginName);
        if (user != null) {
            User user1 = userServiceV1.getUserRepository().getByLoginNameAndLoginPasswd(loginName, loginPass);
            if (user1 == null) {
                user.setLoginPasswd(loginPass);
                userServiceV1.getUserRepository().saveAndFlush(user);
            }
        } else {
            user = new User();
            user.setLoginName(loginName);
            user.setLoginPasswd(EncryptUtil.md5Encode(loginPass));
            user.setUpdateTime(TimeUtil.ymd2date());
            user.setUserName("admin");
            userServiceV1.getUserRepository().saveAndFlush(user);
        }
    }

    /**
     * 测试activemq发布消息
     */
    @Test
    public void testActiveMqPublish() {
        final int addNum = 1000000;
        long startTime, endTime = 0;
        Iterable<Order> orders = this.buildOrderList(addNum);
        //新增数据前，先刪除所有数据
        long recodeNum = orderServiceV1.getOrderRepository().count();
        if (recodeNum > 0) {
            startTime = System.currentTimeMillis();
            boolean delFlag = orderServiceV1.deleteAll();
            endTime = System.currentTimeMillis();
            logger.info("刪除mongodb数据【" + recodeNum + "】条" + (delFlag ? "成功！耗时【" + (endTime - startTime) / 1000 + "】秒" : "失败！"));
        }
        orderServiceV1.addOrdersForAMQ(orders);
    }
}
