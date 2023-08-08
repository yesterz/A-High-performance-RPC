package com.yesterz.basic.service;

import com.alibaba.fastjson.JSONObject;
import com.yesterz.client.annotation.RemoteInvoke;
import com.yesterz.user.remote.UserRemote;
import com.yesterz.user.model.User;
import org.springframework.stereotype.Service;

@Service
public class BasicService {
    @RemoteInvoke
    private UserRemote userRemote;

    public void testSaveUser() {
        User old6 = new User();
        old6.setId(6);
        old6.setName("1个老6");
        Object response = userRemote.saveUser(old6);
        System.out.println("我是客户端，我收到服务器发来的数据，" + JSONObject.toJSONString(response));
    }

}
