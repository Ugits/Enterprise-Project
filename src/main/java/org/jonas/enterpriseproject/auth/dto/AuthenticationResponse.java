package org.jonas.enterpriseproject.auth.dto;

import org.jonas.enterpriseproject.user.authorities.UserRole;

public record AuthenticationResponse(
        String token
) {

}
