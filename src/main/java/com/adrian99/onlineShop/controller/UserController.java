package com.adrian99.onlineShop.controller;

import com.adrian99.onlineShop.exception.ApiRequestException;
import com.adrian99.onlineShop.model.User;
import com.adrian99.onlineShop.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/user")
    public User addUser(@RequestBody User user){
        user.setActive(true);
        user.setRoles("ROLE_USER");
        return userService.save(user);
    }

    @PutMapping("/api/user/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody User userInfo){
        User user = userService.findById(userId);
        if(user == null)
            throw new ApiRequestException("The user do not exists!");
        //TODO FINISH THIS
        return null;
    }

}
