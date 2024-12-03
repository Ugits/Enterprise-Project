package org.jonas.enterpriseproject.api.dto;

public record ErrorResponseDTO(
        int statusCode,
        String message,
        String timestamp
) {
}
