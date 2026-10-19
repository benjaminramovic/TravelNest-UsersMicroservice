package com.travelnest.TravelNestUsers.service;

import com.travelnest.TravelNestUsers.model.RefreshToken;
import com.travelnest.TravelNestUsers.model.Users;
import com.travelnest.TravelNestUsers.repository.RefreshTokenRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefrehTokenService {
    @Autowired
    private RefreshTokenRepo refreshTokenRepository;

    @Value("${jwt.refreshExpiration}")
    private Long refreshTokenDurationMs;


    @Transactional
    public RefreshToken createRefreshToken(Users user) {

        Optional<RefreshToken> existing =
                refreshTokenRepository.findByUser(user);

        //refreshTokenRepository.deleteByUser(user);
        RefreshToken refreshToken;
        if (existing.isPresent()) {
            refreshToken = existing.get();
        } else {
            refreshToken = new RefreshToken();
            refreshToken.setUser(user);
        }

        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken.setExpiryDate(
                Instant.now().plusMillis(refreshTokenDurationMs)
        );

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {

        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {

            refreshTokenRepository.delete(token);

            throw new RuntimeException("Refresh token expired");
        }

        return token;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
}
