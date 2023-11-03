package com.lcwd.UserPortal.UserPortal.controllers;


import com.lcwd.UserPortal.UserPortal.entites.User;
import com.lcwd.UserPortal.UserPortal.exceptions.ResourceNotFoundException;
import com.lcwd.UserPortal.UserPortal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @GetMapping("/{email}/{password}")
    public ResponseEntity<String> login(@PathVariable String email, @PathVariable String password){
       User user= userService.login(email,password);
       Optional<String> userId= Optional.ofNullable(Optional.ofNullable(user.getUserID()).orElseThrow(() ->
               new ResourceNotFoundException("User with given emailID is not found on server !! :" + email)));
        return ResponseEntity.status(HttpStatus.FOUND).body(userId.get());
    }

    //single user get
    @GetMapping("/{userId}")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    //all user get
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }
}
