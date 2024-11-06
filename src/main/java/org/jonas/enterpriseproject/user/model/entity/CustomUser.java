package org.jonas.enterpriseproject.user.model.entity;

import jakarta.persistence.*;

@Entity
@Table("users")
public class CustomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;


    public CustomUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public CustomUser() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}