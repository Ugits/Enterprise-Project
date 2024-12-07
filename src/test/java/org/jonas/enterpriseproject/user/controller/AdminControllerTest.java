package org.jonas.enterpriseproject.user.controller;

import org.jonas.enterpriseproject.user.authorities.UserRole;
import org.jonas.enterpriseproject.user.model.entity.CustomUser;
import org.jonas.enterpriseproject.user.repository.UserRepository;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AdminControllerTest {

    private final MockMvc mockMvc;
    private final UserRepository userRepository;

    @Autowired
    public AdminControllerTest(MockMvc mockMvc, UserRepository userRepository) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        userRepository.save(
                new CustomUser(
                        "Rutger",
                        "Jönåker",
                        UserRole.USER,
                        true,
                        true,
                        true,
                        true
                ));
    }

//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void accessAdminResources() throws Exception {
//        mockMvc.perform(get("/admin"))
//                .andExpect(status().);
//    }

    @Test
    @WithMockUser(roles = "USER")
    void accessAdminControllerUserAuth() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUserWhenAdminAuth() throws Exception {
        mockMvc.perform(delete("/admin/delete-user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("username", "Rutger"))
                .andExpect(status().isNoContent());
    }


    @Test
    @WithMockUser(roles = "USER")
    void deleteUserWhenUserAuth() throws Exception {
        mockMvc.perform(delete("/admin/delete-user")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("username", "Rutger"))
                .andExpect(status().isForbidden());
    }

}