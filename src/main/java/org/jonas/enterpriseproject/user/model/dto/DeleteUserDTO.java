package org.jonas.enterpriseproject.user.model.dto;

import jakarta.validation.constraints.NotBlank;

public record DeleteUserDTO(
        @NotBlank(message = "Username cannot be blank")
        String username

) {
}
