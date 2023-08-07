package com.yesterz.user.remote;

import com.yesterz.netty.annotation.Remote;
import com.yesterz.netty.util.ResponseUtil;
import com.yesterz.user.model.User;
import com.yesterz.user.service.UserService;

import javax.annotation.Resource;
import java.util.List;

@Remote
public class UserRemoteImpl implements UserRemote {

    @Resource
    private UserService userService;

    public Object saveUser(User user) {

        userService.save(user);

        return ResponseUtil.createSuccessResult(user);
    }

    public Object saveUsers(List<User> users) {

        userService.saveList(users);

        return ResponseUtil.createSuccessResult(users);
    }
}
