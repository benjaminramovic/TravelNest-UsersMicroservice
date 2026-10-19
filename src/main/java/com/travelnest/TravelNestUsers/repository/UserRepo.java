package com.travelnest.TravelNestUsers.repository;

import com.travelnest.TravelNestUsers.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
    boolean existsByEmail(String email);
    Users findByEmail(String email);
    boolean existsByUsername(String username);
}
