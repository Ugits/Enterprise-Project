package org.jonas.enterpriseproject.auth.controller;

import org.jonas.enterpriseproject.user.authorities.UserRole;
import org.jonas.enterpriseproject.user.model.entity.CustomUser;
import org.jonas.enterpriseproject.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthControllerTest {

    private final MockMvc mockMvc;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    AuthControllerTest(MockMvc mockMvc, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    void setUp() {
        userRepository.save(
                new CustomUser(
                        "Rutger",
                        passwordEncoder.encode("Jönåker"),
                        UserRole.USER,
                        true,
                        true,
                        true,
                        true
                ));
    }

    @Test
    void registerUser() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "Dick",
                                  "password": "Grayson",
                                  "role": "USER"
                                }
                                """)
                )
                .andExpect(status().isCreated());
    }

    @Test
    void registerAlreadyExistingUser() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "username": "Rutger",
                          "password": "Jönåker",
                          "role": "USER"
                        }
                        """)
                )
                .andExpect(status().isConflict());
    }

    @Test
    void loginReturnsToken() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                        "username": "Rutger",
                        "password": "Jönåker"
                        }
                        """)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void loginBadCredentials() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                        "username": "Conny",
                        "password": "Jönåker"
                        }
                        """)
                )
                .andExpect(status().isUnauthorized());
    }


}