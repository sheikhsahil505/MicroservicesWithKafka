package com.UserService.controller;

import com.UserService.entity.User;
import com.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public User saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("findById/{userId}")
    public Optional<User> getUserById(@PathVariable long userId ){
        return userService.findById(userId);
    }
    @GetMapping("/findAll")
    public  List<User> getAll(){
        return userService.getAll();
    }
}
