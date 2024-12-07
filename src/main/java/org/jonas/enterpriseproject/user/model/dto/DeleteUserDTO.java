package org.jonas.enterpriseproject.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DeleteUserDTO(
        @NotBlank(message = "Username cannot be blank")
        String username

) {
}
