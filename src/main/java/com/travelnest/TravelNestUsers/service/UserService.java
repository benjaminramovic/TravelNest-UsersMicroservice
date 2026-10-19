package com.travelnest.TravelNestUsers.service;

import com.travelnest.TravelNestUsers.dto.LoginRequest;
import com.travelnest.TravelNestUsers.dto.LoginResponse;
import com.travelnest.TravelNestUsers.dto.RefreshTokenRequest;
import com.travelnest.TravelNestUsers.exceptions.DuplicateException;
import com.travelnest.TravelNestUsers.model.RefreshToken;
import com.travelnest.TravelNestUsers.model.UserRole;
import com.travelnest.TravelNestUsers.model.Users;
import com.travelnest.TravelNestUsers.repository.UserRepo;
import com.travelnest.TravelNestUsers.repository.UserRoleRepo;
import jakarta.transaction.Transactional;
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

    @Autowired
    RefrehTokenService refrehTokenService;

    @Autowired
    EmailService emailService;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public List<Users> getAll(){
        return repo.findAll();
    }

    public Users register(Users user){
        if (repo.existsByEmail(user.getEmail())) {

            Users existingUser = repo.findByEmail(user.getEmail());

            if(existingUser.isVerified()) {
                System.out.println("KORISNIK VEĆ POSTOJI I VERIFIKOVAN JE");
            }
            else {
                String token = jwtService.generateVerificationToken(existingUser);
                existingUser.setVerificationToken(token);
                repo.save(existingUser);

            }

            throw new DuplicateException("Email je već zauzet");
        }

        String token2 = jwtService.generateVerificationToken(user);
        user.setVerificationToken(token2);
        user.setPassword(encoder.encode(user.getPassword()));
        UserRole role = roleRepo.findByName("USER");
        user.getRoles().add(role);
        Users newUser = repo.save(user);
        emailService.sendVerificationEmail(user.getEmail(), token2);
        return newUser;

    }

    public LoginResponse verify(LoginRequest credentials) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(),credentials.getPassword()));
        Users user = repo.findByUsername(credentials.getUsername());
        RefreshToken refreshToken =
                refrehTokenService.createRefreshToken(user);


        return new LoginResponse(jwtService.generateKey(user), refreshToken.getToken(), "Login successfull!");
    }

    public LoginResponse refresh(RefreshTokenRequest request){
        String requestToken = request.getToken();

        RefreshToken refreshToken = refrehTokenService
                .findByToken(requestToken)
                .map(refrehTokenService::verifyExpiration)
                .orElseThrow(() ->
                        new RuntimeException("Invalid refresh token"));

        Users user = refreshToken.getUser();

        String accessToken = jwtService.generateKey(user);

        return new LoginResponse(
                accessToken,
                requestToken,
                "Successfull!"
        );
    }

}
