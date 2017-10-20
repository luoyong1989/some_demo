package com.ly.service;

import com.alibaba.fastjson.JSONObject;
import com.ly.pojo.ValueRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理拦截配置
 *
 * @author ly
 * @create 2017-10-18
 **/
@Component
public class MonitorConfigManager {
    private static final Logger logger = LoggerFactory.getLogger(MonitorConfigManager.class);
    private static final PathMatcher pathMatcher = new AntPathMatcher();
    private static List<ValueRoute> routes;

    @PostConstruct
    private void initMapping(){
        String json = fetchJson();
        routes = JSONObject.parseArray(json, ValueRoute.class);
        logger.info("json = \n" + json);
    }

    public ValueRoute macthPath(String requestUri){
        for (ValueRoute route : routes){
            if (route.getRequestUri().equals(requestUri)){
                return route;
            }
            if (pathMatcher.match(route.getRequestUri(),requestUri)){
                return route;
            }
        }
        return null;
    }

    private String fetchJson(){
        List<ValueRoute> list = new ArrayList<>();
        ValueRoute route = new ValueRoute();
        route.setAppIdPath("valueB.sex");
        route.setArgsIndex(0);
        route.setRequestUri("/getData/{id}");
        list.add(route);

        ValueRoute route2 = new ValueRoute();
        route2.setArgsIndex(0);
        route2.setRequestUri("/getInfo");
        list.add(route2);
        String json = JSONObject.toJSONString(list);
        return json;
    }

}
