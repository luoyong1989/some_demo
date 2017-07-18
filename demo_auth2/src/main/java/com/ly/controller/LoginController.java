package com.ly.controller;

import com.ly.dto.AuthInfo;
import com.ly.service.ClientService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by ly on 2017/7/17.
 */
@Controller
public class LoginController {
    private static Map<String, Object> codeMap = new HashMap<>();

    @Autowired
    ClientService clientService;

    @RequestMapping("/login")
    public Object login(String appid, String redirect_uri, String scope, String state, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            System.out.println("已经登录过了。。。。。。。");
            AuthInfo authInfo = (AuthInfo) request.getSession().getAttribute("authInfo");
            return "redirect:"+authInfo.getRedirect_uri();
        }
        if (!clientService.checkClientId(appid)) {
            return "static/html/error.html";
        }
        AuthInfo authInfo = new AuthInfo();
        authInfo.setAppid(appid);
        authInfo.setRedirect_uri(redirect_uri);
        authInfo.setScope(scope);
        authInfo.setState(state);
        request.getSession().setAttribute("authInfo", authInfo);
        return "static/html/login.html?appid=" + appid + "&scope=" + scope;
    }

    @RequestMapping("/doLogin")
    public String submitLogin(String username, String password, HttpServletRequest request) {
        AuthInfo authInfo = (AuthInfo) request.getSession().getAttribute("authInfo");
        try {
            AuthenticationToken token = new UsernamePasswordToken(username, password);
            SecurityUtils.getSubject().login(token);
            System.out.println("登陆成功");
            UUID uuid = UUID.randomUUID();
            String code = uuid.toString();
            authInfo.setCode(code);
            String url = "redirect:" + authInfo.getRedirect_uri() + "?code=" + code + "&state=" + authInfo.getState();
            System.out.println("redirect to " + url);
            codeMap.put(code, authInfo.getAppid());
            return url;
        } catch (Exception e) {
            System.out.println("登陆失败");
            return "static/html/login.html";
        }
    }

    @RequestMapping("/token")
    @ResponseBody
    public Object token(HttpServletRequest request, String appid, String appSecret, String code) {
        Map<String, String> rMap = new HashMap<>();
        if (codeMap.containsKey(code)) {
            String cathAppid = (String) codeMap.get(code);
            if (cathAppid.equals(appid)) {
                if (clientService.checkSecuret(appid, appSecret)) {
                    rMap.put("status", "200");
                    String token = "token:" + appid + code + (new Date().getTime());
                    rMap.put("token", token);
                    return rMap;
                }
            }
        }
        rMap.put("status", "1000");
        rMap.put("msg", "身份验证失败");
        return rMap;

    }


    @RequestMapping("/loginSuccess")
    public Object loginSuccess() {
        System.out.println("登陆成功");
        return "static/index.html";
    }

}
