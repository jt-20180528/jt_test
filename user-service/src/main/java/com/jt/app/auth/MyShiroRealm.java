package com.jt.app.auth;

import com.jt.app.model.Resource;
import com.jt.app.model.Role;
import com.jt.app.service.ResourceServiceV1;
import com.jt.app.service.RoleServiceV1;
import com.jt.app.service.UserServiceV1;
import com.jt.app.model.User;
import com.jt.app.util.EncryptUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyShiroRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    private RoleServiceV1 roleServiceV1;
    @Autowired
    private ResourceServiceV1 resourceServiceV1;
    @Autowired
    private UserServiceV1 userServiceV1;

    //角色权限和对应权限添加
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        User user = (User) principalCollection.getPrimaryPrincipal();

        logger.info("登陆账号：" + user.getUserName());

        //查询用户名称
        List<Role> roles = roleServiceV1.getRolesByIds(user.getId());

        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : roles) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            logger.info("账号角色：" + role.toString());
        }

        List<Resource> resources = resourceServiceV1.getResourcesByIds(roles);
        for (Resource resource : resources) {
            //添加权限
            simpleAuthorizationInfo.addStringPermission(resource.getResourceName());
            logger.info("角色权限：" + resource.toString());
        }
        return simpleAuthorizationInfo;
    }

    //用户认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        String password = new String((char[]) authenticationToken.getCredentials()).trim();
        User user = userServiceV1.getByLoginName(name);

        if (user == null) {
            //这里返回后会报出对应异常
            return null;
        } else if (!EncryptUtil.md5Encode(password).equals(user.getLoginPasswd())) {
            throw new IncorrectCredentialsException();
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            //SimpleAuthenticationInfo(user,*,*) user是把当前登录用户对象存入，在principalCollection.getPrimaryPrincipal()中就可以直接获取用户对象，
            // 如果存放的是用户名，则只能获取用户名
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,
                    password, getName());
            Subject subject = SecurityUtils.getSubject();
            subject.getSession().setAttribute(getName(), user);
            return simpleAuthenticationInfo;
        }
    }

}
