package org.jonas.enterpriseproject.exception;

import java.util.List;

public record ErrorResponse(
        int statusCode,
        String message,
        String timestamp,
        List<ValidationError> errors
) {

    public ErrorResponse(int statusCode, String message, String timestamp) {
        this(statusCode, message, timestamp, List.of());
    }

    public record ValidationError(
            String field,
            String error
    ) {

    }
}
