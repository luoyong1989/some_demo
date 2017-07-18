package com.ly.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.apache.shiro.web.filter.mgt.DefaultFilter.user;

/**
 * Created by ly on 2017/7/17.
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("权限认证方法：MyShiroRealm.doGetAuthenticationInfo()");
        SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
        Set<String> roleSet = new HashSet<String>();
        roleSet.add("100002");
        info.setRoles(roleSet);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Map<String,String> user = new HashMap<>();
        user.put("name",token.getUsername());
        user.put("pwd",token.getPassword().toString());
        user.put("role","admin");

        if ("admin".equals(token.getUsername()) && "123".equals(new String(token.getPassword()))){
            return new SimpleAuthenticationInfo(user, token.getPassword(), getName());
        }else {
            throw new AccountException("帐号或密码不正确！");
        }
    }
}
