package com.travelnest.TravelNestUsers.controller;

import com.travelnest.TravelNestUsers.model.Users;
import com.travelnest.TravelNestUsers.repository.UserRepo;
import com.travelnest.TravelNestUsers.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationController {
    @Autowired
    private UserRepo myAppUserRepository;

    @Autowired
    private JwtService jwtUtil;

    @GetMapping("/req/signup/verify")
    public ResponseEntity verifyEmail(@RequestParam("token") String token) {
        if (!jwtUtil.isVerificationToken(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Nevažeći verifikacioni token.");
        }

        String emailString = jwtUtil.extractEmail(token);
        Users user = myAppUserRepository.findByEmail(emailString);
        if (user == null || user.getVerificationToken() == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token Expired!");
        }

        if (jwtUtil.isTokenExpired(token) || !user.getVerificationToken().equals(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token Expired!");
        }
        user.setVerificationToken(null);
        user.setVerified(true);
        myAppUserRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Email successfully verified!");
    }
}
