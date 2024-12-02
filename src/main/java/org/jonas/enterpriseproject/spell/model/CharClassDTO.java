package org.jonas.enterpriseproject.spell.model;

import jakarta.validation.constraints.Size;

public record CharClassDTO(
        @Size(max = 30, message = "Index cannot exceed 30 characters")
        String index,

        @Size(max = 30, message = "Name cannot exceed 30 characters")
        String name
) {
}
