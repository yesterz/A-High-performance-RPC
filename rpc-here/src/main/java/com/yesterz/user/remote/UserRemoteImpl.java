package com.yesterz.user.remote;

import com.yesterz.netty.annotation.Remote;
import com.yesterz.netty.util.Response;
import com.yesterz.netty.util.ResponseUtil;
import com.yesterz.user.bean.User;
import com.yesterz.user.service.UserService;

import javax.annotation.Resource;
import java.util.List;

@Remote
public class UserRemoteImpl implements UserRemote{

    @Resource
    private UserService userService;

    public Response saveUser(User user) {

        userService.save(user);


        return ResponseUtil.createSuccessResult(user);
    }

    public Response saveUsers(List<User> userList) {

        userService.saveList(userList);

        return ResponseUtil.createSuccessResult(userList);
    }
}
