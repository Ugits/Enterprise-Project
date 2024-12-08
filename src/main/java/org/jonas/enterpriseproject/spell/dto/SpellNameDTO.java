package org.jonas.enterpriseproject.spell.dto;

import jakarta.validation.constraints.NotBlank;

public record SpellNameDTO(
        @NotBlank
        String name
) {
}
