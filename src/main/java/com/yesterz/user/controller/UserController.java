package com.yesterz.user.controller;

import com.yesterz.netty.util.Response;
import com.yesterz.netty.util.ResponseUtil;
import com.yesterz.user.bean.User;
import com.yesterz.user.service.UserService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    public Response saveUser(User user) {

        userService.save(user);

        return ResponseUtil.createSuccessResult();
    }

    public Response saveUsers(List<User> users) {

        userService.saveList(users);

        return ResponseUtil.createSuccessResult();
    }
}
