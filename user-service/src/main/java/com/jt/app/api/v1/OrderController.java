package com.jt.app.api.v1;

import com.jt.app.exception.JsonResult;
import com.jt.app.exception.RespCode;
import com.jt.app.model.mongodb.Order;
import com.jt.app.service.mongodb.OrderServiceV1;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order/v1")
public class OrderController {

    @Autowired
    private OrderServiceV1 orderServiceV1;

    @GetMapping("/getOrdersAll")
    public JsonResult getOrdersAll(HttpServletRequest request){
        JsonResult jsonResult = new JsonResult(request.getRequestURI());
        jsonResult.setRespCode(RespCode.SUCCESS);
        jsonResult.setResultData(orderServiceV1.getOrderRepository().findAll());
        return jsonResult;
    }

    @PostMapping("/addOrder")
    public JsonResult addOrder(HttpServletRequest request, Order order){
        JsonResult jsonResult = new JsonResult(request.getRequestURI());
        jsonResult.setRespCode(RespCode.SUCCESS);
        jsonResult.setResultData(orderServiceV1.getOrderRepository().save(order));
        return jsonResult;
    }
}
