package com.jt.app.api.v1;

import com.jt.app.service.UserServiceV1;
import com.jt.app.exception.JsonResult;
import com.jt.app.exception.RespCode;
import com.jt.app.model.User;
import com.jt.app.util.EncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@ResponseBody
@RequestMapping("/user/v1")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController() {
    }

    private UserServiceV1 userServiceV1;

    @Autowired
    public UserController(UserServiceV1 userServiceV1) {
        this.userServiceV1 = userServiceV1;
    }

    /**
     * 获取用户
     *
     * @param request
     * @param id
     * @return
     */
    @GetMapping("/getUserById/{id}")
    public JsonResult getUserById(HttpServletRequest request, @PathVariable Integer id) {
        JsonResult jsonResult = new JsonResult(request.getRequestURI());
        jsonResult.setRespCode(RespCode.SUCCESS);
        //jsonResult.setResultData(userServiceV1.getUserById(id));
        jsonResult.setResultData(userServiceV1.getUserRepository().getById(id));
        return jsonResult;
    }

    /**
     * 根据账号密码获取用户
     *
     * @param request
     * @param userName
     * @return
     */
    @GetMapping("/getByUserName/{userName}")
    public JsonResult getByUserName(HttpServletRequest request, @PathVariable String userName) {
        JsonResult jsonResult = new JsonResult(request.getRequestURI());
        User user = userServiceV1.getUserRepository().getByUserName(userName);
        jsonResult.setRespCode(RespCode.SUCCESS);
        if (user == null) {
            jsonResult.setRespCode(RespCode.LOGIN_FAIL);
        }
        jsonResult.setResultData(user);
        return jsonResult;
    }

}
