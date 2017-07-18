package com.ly.shiro;


import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

public class CrossDomainFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(CrossDomainFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String log = MessageFormat.format("uri:'{'{0}'}', method:'{'{2}'}', parameters:{3}",
                request.getRequestURI(),
                request.getMethod(),
                JSONObject.toJSONString(request.getParameterMap())
        );
        logger.info(log);

        response.addHeader("Access-Control-Allow-Credentials", "true");
        // 设置允许跨域访问的域名，* 表示任何跨域访问
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8000");
        response.addHeader("Access-Control-Allow-Methods", "OPTIONS,POST,GET,DELETE,PUT");
        response.addHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");

        response.setContentType("textml;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("XDomainRequestAllowed","1");

        // 跨域尝试请求放行
        if(request.getMethod().equals("OPTIONS")){
            return;
        }

        filterChain.doFilter(servletRequest, response);//放行。让其走到下个链或目标资源中
    }

    @Override
    public void destroy() {}

}