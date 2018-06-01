package com.jt.app.api.v1;

import com.jt.app.model.Resource;
import com.jt.app.model.Role;
import com.jt.app.service.ResourceServiceV1;
import com.jt.app.service.RoleServiceV1;
import com.jt.app.service.UserServiceV1;
import com.jt.app.model.User;
import com.jt.app.util.EncryptUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018/5/20
 * @Description:
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceV1 userServiceV1;
    @Autowired
    private RoleServiceV1 roleServiceV1;
    @Autowired
    private ResourceServiceV1 resourceServiceV1;

    @GetMapping("/login")
    public String login() {
        logger.info("request input getLogin");
        return "login";
    }

    /**
     * 登入
     */
    @PostMapping("/login")
    public Object login(Model model, String userName, String password) {
        UsernamePasswordToken usernamePasswordToken = null;
        Subject subject = null;
        try {
            subject = SecurityUtils.getSubject();
            usernamePasswordToken = new UsernamePasswordToken(userName, password);
            subject.login(usernamePasswordToken);
            logger.info("账号：" + userName + "-密码：" + password + "sessionId:" + subject.getSession().getId());
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误");
        } catch (LockedAccountException e) {
            model.addAttribute("msg", "登录失败，该用户已被冻结");
        } catch (AuthenticationException e) {
            model.addAttribute("msg", "该用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //验证是否登录成功
        if (subject.isAuthenticated()) {
            logger.info("用户[" + userName + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            model.addAttribute("msg", "用户【" + userName + "】登陆成功！sessionId: " + subject.getSession().getId());

            //获取session中对象
            Object obj = subject.getSession().getAttribute(userName);
            User user = null;
            if (obj == null) {
                user = userServiceV1.getUserRepository().getByLoginNameAndLoginPasswd(userName, EncryptUtil.md5Encode(password));
            } else {
                user = (User) obj;
            }
            if (user != null) {
                //获取角色信息
                List<Role> roles = roleServiceV1.getRolesByIds(user.getId());
                model.addAttribute("roles", roles);

                //获取资源信息
                List<Resource> resources = resourceServiceV1.getResourcesByIds(roles);
                model.addAttribute("resources", resources);
            }

            return "index";
        } else {
            usernamePasswordToken.clear();
            return "login";
        }
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    //登出
    @GetMapping("/logout")
    public String logout() {
        /*SecurityUtils.getSubject().logout();
        redirectAttributes.addFlashAttribute("message", "您已安全退出");*/
        return "logout";
    }

    //错误页面展示
    @GetMapping("/error")
    public String error() {
        return "error";
    }

}
