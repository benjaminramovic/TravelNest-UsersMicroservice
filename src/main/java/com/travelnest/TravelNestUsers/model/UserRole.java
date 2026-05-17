package com.travelnest.TravelNestUsers.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<Users> users = new HashSet<>();


    public String getName() {
        return name;
    }

}
