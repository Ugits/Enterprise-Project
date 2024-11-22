package org.jonas.enterpriseproject.authentication.dto;

public record AuthenticationRequest(
        String username,
        String password
) {
}
