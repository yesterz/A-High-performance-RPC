package com.yesterz.user.controller;

import com.yesterz.user.bean.User;
import com.yesterz.user.service.UserService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    public void saveUser(User user) {
        userService.save(user);
    }
}
