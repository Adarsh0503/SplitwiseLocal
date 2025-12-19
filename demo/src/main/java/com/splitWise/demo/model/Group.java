package com.splitWise.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups")   // ✅ avoid SQL keyword "group"
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    private List<User> members;

    public Group() {}

    public Group(String name, List<User> members) {
        this.name = name;
        this.members = members;
    }

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
