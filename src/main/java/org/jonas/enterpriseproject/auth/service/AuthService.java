package org.jonas.enterpriseproject.auth.service;

import org.jonas.enterpriseproject.auth.dto.AuthenticationRequest;
import org.jonas.enterpriseproject.auth.dto.AuthenticationResponse;
import org.jonas.enterpriseproject.auth.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse verify(AuthenticationRequest authenticationRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authenticationRequest.username(),
                                authenticationRequest.password()
                        ));

        String generatedToken = jwtService.generateToken(authenticationRequest.username());
        System.out.println("Generated token: " + generatedToken);

        return
                new AuthenticationResponse(
                        generatedToken,
                        authentication.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .filter(authority -> authority.startsWith("ROLE_"))
                                .findFirst()
                                .orElseThrow(() -> new IllegalStateException("User has no role"))
                                .substring(5));

    }

}
