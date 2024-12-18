package org.jonas.enterpriseproject.auth.controller;

import jakarta.validation.Valid;
import org.jonas.enterpriseproject.auth.dto.AuthenticationRequest;
import org.jonas.enterpriseproject.auth.dto.AuthenticationResponse;
import org.jonas.enterpriseproject.auth.service.AuthService;
import org.jonas.enterpriseproject.user.model.dto.SignupRequestDTO;
import org.jonas.enterpriseproject.user.model.dto.UserCredentialsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authService.verify(authenticationRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserCredentialsDTO> register(@RequestBody @Valid SignupRequestDTO signupRequestDTO) {
        return authService.createUser(signupRequestDTO);
    }



}
