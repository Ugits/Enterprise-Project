package org.jonas.enterpriseproject.user.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    private final MockMvc mockMvc;

    @Autowired
    UserControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void accessUserResourcesNoAuth() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void doesUserControllerExist(ApplicationContext applicationContext) throws Exception {
        assertNotNull(applicationContext.getBean(UserController.class));
    }

    @Test
    @WithMockUser(username = "Dick", password = "Grayson", roles="USER")
    void fetchCredentialsUserAuth() throws Exception {
        mockMvc.perform(get("/user/credentials"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Dick"))
                .andExpect(jsonPath("$.password").value("Grayson"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

}