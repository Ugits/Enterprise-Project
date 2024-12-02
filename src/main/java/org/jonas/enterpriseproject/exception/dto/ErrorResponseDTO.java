package org.jonas.enterpriseproject.exception.dto;

public record ErrorResponseDTO(
        int statusCode,
        String message,
        String timestamp
) {
}
