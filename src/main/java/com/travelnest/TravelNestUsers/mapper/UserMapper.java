package com.travelnest.TravelNestUsers.mapper;

import com.travelnest.TravelNestUsers.dto.RegisterUser;
import com.travelnest.TravelNestUsers.model.Users;

public class UserMapper {

    public static Users mapToUser(RegisterUser newUser){
        Users user = new Users();
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPhone(newUser.getPhone());
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
        user.setEnabled(newUser.getEnabled());
        return user;
    }
}
