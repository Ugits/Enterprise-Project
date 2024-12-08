package org.jonas.enterpriseproject.spell.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SpellIdDTO(
        @NotNull(message = "ID cannot be null.")
        @Min(value = 1, message = "ID must be a positive number.")
        Long id
) {
}
