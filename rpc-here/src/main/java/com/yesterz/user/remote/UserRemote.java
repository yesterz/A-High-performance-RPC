package com.yesterz.user.remote;

import com.yesterz.netty.util.Response;
import com.yesterz.user.bean.User;

import java.util.List;

public interface UserRemote {
    public Response saveUser(User user);
    public Response saveUsers(List<User> users);

}
