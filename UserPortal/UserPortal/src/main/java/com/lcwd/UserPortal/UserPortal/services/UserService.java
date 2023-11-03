package com.lcwd.UserPortal.UserPortal.services;



import com.lcwd.UserPortal.UserPortal.entites.User;

import java.util.List;

public interface UserService {

    //User operation

    //create
    User saveUser(User user);

    //get all user
    List<User> getAllUser();

    //get single user based on userId
    User getUser(String userId);

    //get id by email & password
    User login(String email, String password);

    //TODO : delete
    //TODO : update

}
