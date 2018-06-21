package com.jt.app.api.v1;


import com.jt.app.model.Msg;
import com.jt.app.util.TimeUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WebSocketController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RequestMapping("/sockjs/websk")
    public ModelAndView toSockjsWebsk(ModelAndView mv) {
        mv.setViewName("/websocket/sockjs/websk");
        return mv;
    }
    @RequestMapping("/h5/websk")
    public ModelAndView toH5Websk(ModelAndView mv) {
        mv.setViewName("/websocket/h5/websk");
        return mv;
    }

    @MessageMapping("/sendTest")
    @SendTo("/topic/subscribeTest")
    public Object sendDemo(Msg message) {
        //logger.info("接收到了信息" + message.getName());
        //messagingTemplate.convertAndSend("/topic/subscribeTest", message.getName());
        //组装content
        Msg msg = new Msg();
        Map<String,Object> content = new HashMap<>();
        content.put("msg","点击广播测试");
        try {
            content.put("sendTime", TimeUtil.ymdHms2str());
            msg.setContent(content);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("类型转化出错！");
        }
        return msg;
    }

    /**
     * 单播消息推送
     * @param userId
     * @param headerAccessor
     * @return
     */
    @MessageMapping("/sendToUser")
    @SendToUser("/queue/user/a1")
    public Object sendToUser(String userId, StompHeaderAccessor headerAccessor){
        Msg msg = new Msg();
        msg.setContent("这是"+userId+"的消息！");
        return msg;
    }

    /**
     * 测试发送到广播消息
     */
    @RequestMapping("/testSendMsg")
    public void testSendMsg(){
        Msg msg = new Msg("asd","asdddddddddddd");
        messagingTemplate.convertAndSend("/topic/subscribeTest",msg);
    }

    /**
     * 测试发送到单播消息
     */
    @RequestMapping("/testSendMsgToUser")
    public void testSendMsgToUser(){
        Msg msg = new Msg("asd","444444444444444444");
        messagingTemplate.convertAndSendToUser("001","/queue/user/a1",msg);
    }
}
