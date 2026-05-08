package com.travelnest.TravelNestUsers.repository;

import com.travelnest.TravelNestUsers.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);
}
