package com.ly.service;

import com.ly.entity.ClientInfo;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ly on 2017/7/18.
 */
@Service
public class ClientService {
    private static Map<String ,ClientInfo> map  = new HashMap<>();

    @PostConstruct
    private void init(){
        map.put("clientId001",new ClientInfo("clientId001","123321"));
        map.put("clientId002",new ClientInfo("clientId001","123321"));
        map.put("clientId003",new ClientInfo("clientId001","123321"));
    }

    public boolean checkClientId(String clientId){
        return map.containsKey(clientId);
    }

    public boolean checkSecuret(String clientId,String securet){
        if (map.containsKey(clientId)){
            ClientInfo info = map.get(clientId);
            if (securet.equals(info.getSecuret())){
                return true;
            }
        }
        return false;
    }
}
