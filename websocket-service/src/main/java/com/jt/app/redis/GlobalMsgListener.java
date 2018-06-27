package com.jt.app.redis;

import com.jt.app.service.LotteryUserServiceV1;
import com.jt.app.service.TenementServiceV1;
import com.jt.app.service.UserServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GlobalMsgListener implements MsgReceive{

    @Autowired
    private LotteryUserServiceV1 lotteryUserServiceV1;
    @Autowired
    private UserServiceV1 userServiceV1;
    @Autowired
    private TenementServiceV1 tenementServiceV1;

    @Override
    public void getMsg(Object msg) {
        //TODO 根据消息头判断是哪个业务的逻辑进行推送处理
        if(msg instanceof Integer){
            lotteryUserServiceV1.getTenementRepository();
        }
    }
}
