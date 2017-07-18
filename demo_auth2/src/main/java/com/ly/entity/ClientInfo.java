package com.ly.entity;

/**
 * Created by ly on 2017/7/18.
 */
public class ClientInfo {
    private String clientId;
    private String securet;

    public ClientInfo(String clientId, String securet) {
        this.clientId = clientId;
        this.securet = securet;
    }

    public ClientInfo() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecuret() {
        return securet;
    }
}
