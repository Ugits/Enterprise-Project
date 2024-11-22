package org.jonas.enterpriseproject.auth.dto;

public record AuthenticationRequest(
        String username,
        String password
) {
}
