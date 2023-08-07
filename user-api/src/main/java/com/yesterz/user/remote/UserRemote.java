package com.yesterz.user.remote;


import com.yesterz.user.model.User;

import java.util.List;

public interface UserRemote {
    public Object saveUser(User user);
    public Object saveUsers(List<User> users);

}
