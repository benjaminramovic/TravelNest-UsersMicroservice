package com.travelnest.TravelNestUsers.service;

import com.travelnest.TravelNestUsers.dto.LoginRequest;
import com.travelnest.TravelNestUsers.dto.LoginResponse;
import com.travelnest.TravelNestUsers.model.UserRole;
import com.travelnest.TravelNestUsers.model.Users;
import com.travelnest.TravelNestUsers.repository.UserRepo;
import com.travelnest.TravelNestUsers.repository.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo repo;

    @Autowired
    UserRoleRepo roleRepo;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtService jwtService;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public List<Users> getAll(){
        return repo.findAll();
    }

    public Users register(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        UserRole role = roleRepo.findByName("USER");
        user.getRoles().add(role);
        return repo.save(user);
    }

    public LoginResponse verify(LoginRequest credentials) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(),credentials.getPassword()));
        if(authentication.isAuthenticated()){
            return new LoginResponse(jwtService.generateKey(credentials.getUsername()), "Login successfull!");
        }
        return new LoginResponse(null, "Invalid username or password!");
    }
}
