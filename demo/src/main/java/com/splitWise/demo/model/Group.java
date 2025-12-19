package com.splitWise.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    private List<User> members;

    // constructors
    public Group() {}

    public Group(String name, List<User> members) {
        this.name = name;
        this.members = members;
    }

    // getters & setters
}
