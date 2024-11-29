package org.jonas.enterpriseproject.user.model.dto;

import jakarta.validation.constraints.*;

public record CustomUserDTO(

        @NotBlank(message = "Username cannot be blank")
        @Size(min = 4, max = 20, message = "Username size bust be between 4 and 20 characters")
        String username,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 7, max = 30, message = "Password size bust be between 7 and 30 characters")
        String password
) {
        public CustomUserDTO(String username) {
                this(username, "");
        }
}
