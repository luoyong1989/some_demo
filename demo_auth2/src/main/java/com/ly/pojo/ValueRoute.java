package com.ly.pojo;

/**
 * 获取参数路径
 *
 * @author ly
 * @create 2017-10-18
 **/

public class ValueRoute {
    private String appIdPath;
    private int argsIndex;
    private String requestUri;

    public String getAppIdPath() {
        return appIdPath;
    }

    public void setAppIdPath(String appIdPath) {
        this.appIdPath = appIdPath;
    }

    public int getArgsIndex() {
        return argsIndex;
    }

    public void setArgsIndex(int argsIndex) {
        this.argsIndex = argsIndex;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }
}
