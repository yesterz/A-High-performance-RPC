package com.yesterz.user.remote;

import com.yesterz.netty.util.Response;
import com.yesterz.user.bean.User;

import java.util.List;

public interface UserRemote {
    public Object saveUser(User user);
    public Object saveUsers(List<User> users);

}
