package com.jt.app.api.v1;


import com.jt.app.config.MyHandler;
import com.jt.app.model.Msg;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;

@RestController
public class WebSocketController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    MyHandler handler;

    @RequestMapping("/websk")
    public ModelAndView toWebsk(ModelAndView mv) {
        mv.setViewName("/websocket/websk");
        return mv;
    }

    @MessageMapping("/sendTest")
    @SendTo("/topic/subscribeTest")
    public Msg sendDemo(Msg message) {
        logger.info("接收到了信息" + message.getName());
        messagingTemplate.convertAndSend("/topic/subscribeTest", message.getName());
        return new Msg("你发送的消息为:" + message.getName());
    }

    @SubscribeMapping("/subscribeTest")
    public Msg sub() {
        logger.info("XXX用户订阅了我。。。");
        return new Msg("感谢你订阅了我。。。");
    }

    /////////////v2版本/////////////
    @RequestMapping("/message")
    public String sendMessage() {
        boolean hasSend = handler.sendMessageToUser(4, new TextMessage("发送一条小xi"));
        System.out.println(hasSend);
        return "message";
    }
}
