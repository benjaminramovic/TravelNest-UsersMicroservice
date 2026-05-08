package com.travelnest.TravelNestUsers.controller;

import com.travelnest.TravelNestUsers.dto.LoginRequest;
import com.travelnest.TravelNestUsers.dto.LoginResponse;
import com.travelnest.TravelNestUsers.dto.RegisterUser;
import com.travelnest.TravelNestUsers.mapper.UserMapper;
import com.travelnest.TravelNestUsers.model.Users;
import com.travelnest.TravelNestUsers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService service;



    @PostMapping("/register")
    public Users register(@RequestBody RegisterUser user){
        return service.register(UserMapper.mapToUser(user));
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest user){
        return service.verify(user);
    }
}
