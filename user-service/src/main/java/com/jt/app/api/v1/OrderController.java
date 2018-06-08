package com.jt.app.api.v1;

import com.jt.app.exception.JsonResult;
import com.jt.app.exception.RespCode;
import com.jt.app.model.mongodb.Order;
import com.jt.app.redis.GlobalCacheKey;
import com.jt.app.redis.service.RedisService;
import com.jt.app.service.mongodb.OrderServiceV1;
import com.jt.app.util.PageUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@ResponseBody
@RequestMapping("/order/v1")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderServiceV1 orderServiceV1;

    @GetMapping("/getOrders")
    public JsonResult getOrders(HttpServletRequest request, PageUtil pageUtil, Order order) {
        JsonResult jsonResult = new JsonResult(request.getRequestURI());
        jsonResult.setRespCode(RespCode.SUCCESS);
        try {
            jsonResult.setResultData(orderServiceV1.getOrders(pageUtil, order));
        } catch (Exception e) {
            jsonResult.setRespCode(RespCode.PARAMETER_ERROR);
        }
        logger.info("从数据库中获取数据！");
        return jsonResult;
    }

    @PostMapping("/addOrder")
    public JsonResult addOrder(HttpServletRequest request, Order order) {
        JsonResult jsonResult = new JsonResult(request.getRequestURI());
        jsonResult.setRespCode(RespCode.SUCCESS);
        jsonResult.setResultData(orderServiceV1.getOrderRepository().save(order));
        return jsonResult;
    }

    @GetMapping("/getByOrderId/{id}")
    public JsonResult getByOrderId(HttpServletRequest request, @PathVariable Integer id) {
        JsonResult jsonResult = new JsonResult(request.getRequestURI());
        jsonResult.setRespCode(RespCode.SUCCESS);
        jsonResult.setResultData(orderServiceV1.getOrderRepository().findOne(id));
        return jsonResult;
    }

    @GetMapping("/getByOrderCode/{orderCode}")
    @Cacheable(value = GlobalCacheKey.order_key, key = "#orderCode")
    public JsonResult getByOrderCode(HttpServletRequest request, @PathVariable String orderCode) {
        JsonResult jsonResult = new JsonResult(request.getRequestURI());
        jsonResult.setRespCode(RespCode.SUCCESS);
        jsonResult.setResultData(orderServiceV1.getOrderRepository().findByOrderCode(orderCode));
        logger.info("从数据库中获取数据！");
        return jsonResult;
    }

    @PutMapping("/updateOrder")
    @CachePut(value = GlobalCacheKey.order_key, key = "#order.orderCode")
    public JsonResult updateOrder(HttpServletRequest request, Order order) {
        JsonResult jsonResult = new JsonResult(request.getRequestURI());
        jsonResult.setRespCode(RespCode.SUCCESS);
        jsonResult.setResultData(orderServiceV1.getOrderRepository().save(order));
        logger.info("从数据库中获取数据！");
        return jsonResult;
    }

    @GetMapping("/deleteByOrderCode/{orderCode}")  //测试使用，开发环境应该是delete方法，由ajax传来
    //@DeleteMapping("/deleteByOrderCode/{orderCode}")
    @CacheEvict(value = GlobalCacheKey.order_key, key = "#orderCode")
    public JsonResult deleteByOrderCode(HttpServletRequest request, @PathVariable String orderCode) {
        JsonResult jsonResult = new JsonResult(request.getRequestURI());
        jsonResult.setRespCode(RespCode.SUCCESS);
        try {
            orderServiceV1.getOrderRepository().deleteByOrderCode(orderCode);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setRespCode(RespCode.REQUEST_ERROR);
        }
        jsonResult.setResultData(null);
        return jsonResult;
    }


}
