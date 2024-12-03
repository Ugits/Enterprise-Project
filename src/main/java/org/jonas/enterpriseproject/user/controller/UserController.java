package org.jonas.enterpriseproject.user.controller;

import jakarta.validation.Valid;
import org.jonas.enterpriseproject.user.model.dto.CustomUserDTO;
import org.jonas.enterpriseproject.user.model.dto.SignupRequestDTO;
import org.jonas.enterpriseproject.user.model.dto.UserCredentialsDTO;
import org.jonas.enterpriseproject.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/credentials")
    public ResponseEntity<UserCredentialsDTO> getCredentials(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(userService.extractCredentials(userDetails));
    }


}
