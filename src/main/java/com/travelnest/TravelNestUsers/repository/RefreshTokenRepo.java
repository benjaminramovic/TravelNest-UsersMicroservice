package com.travelnest.TravelNestUsers.repository;

import com.travelnest.TravelNestUsers.model.RefreshToken;
import com.travelnest.TravelNestUsers.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByUser(Users user);
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(Users user);
}
