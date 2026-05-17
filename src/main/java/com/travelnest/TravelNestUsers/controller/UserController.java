package com.travelnest.TravelNestUsers.controller;

import com.travelnest.TravelNestUsers.dto.LoginRequest;
import com.travelnest.TravelNestUsers.dto.LoginResponse;
import com.travelnest.TravelNestUsers.dto.RegisterUser;
import com.travelnest.TravelNestUsers.mapper.UserMapper;
import com.travelnest.TravelNestUsers.model.Users;
import com.travelnest.TravelNestUsers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService service;


    @GetMapping("/")
    public List<Users> getAll(){
        return service.getAll();
    }


    @PostMapping("/register")
    public Users register(@RequestBody RegisterUser user){
        return service.register(UserMapper.mapToUser(user));
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest user){
        return service.verify(user);
    }

//    @PostMapping("/refresh")
//    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> payload) {
//        String requestToken = payload.get("refreshToken");
//        return refreshTokenRepository.findByToken(requestToken)
//                .map(token -> {
//                    if (refreshTokenService.isTokenExpired(token)) {
//                        refreshTokenRepository.delete(token);
//                        return ResponseEntity.badRequest().body("Refresh token expired. Please login again.");
//                    }
//                    String newJwt = service.generateToken(token.getUser().getUsername());
//                    return ResponseEntity.ok(Map.of("token", newJwt));
//                })
//                .orElse(ResponseEntity.badRequest().body("Invalid refresh token."));
//    }
}
