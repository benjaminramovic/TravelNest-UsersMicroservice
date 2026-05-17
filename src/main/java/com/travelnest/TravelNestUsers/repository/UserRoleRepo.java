package com.travelnest.TravelNestUsers.repository;

import com.travelnest.TravelNestUsers.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {
    UserRole findByName(String user);
}
