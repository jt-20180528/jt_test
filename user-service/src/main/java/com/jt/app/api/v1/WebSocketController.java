package com.jt.app.api.v1;

import com.jt.app.exception.JsonResult;
import com.jt.app.exception.RespCode;
import com.jt.app.model.mongodb.Order;
import com.jt.app.service.mongodb.OrderServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/webSocket/v1")
@ResponseBody
public class WebSocketController {

    @Autowired
    private StringRedisTemplate msgTemplate;

    @Autowired
    private OrderServiceV1 orderServiceV1;

    //get测试 http://127.0.0.1:8086/webSocket/v1/sendMsg/ORDER_BATCH-LATEST_ORDER
    @GetMapping("/sendMsg/{msg}")
    public JsonResult sendMsg(HttpServletRequest request, @PathVariable  String msg){
        JsonResult jsonResult = new JsonResult(request.getRequestURI());
        jsonResult.setRespCode(RespCode.SUCCESS);
        jsonResult.setResultData(orderServiceV1.sendMsg(msg));
        return jsonResult;
    }
}
