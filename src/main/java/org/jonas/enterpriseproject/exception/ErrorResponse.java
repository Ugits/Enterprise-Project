package org.jonas.enterpriseproject.exception;

import java.time.OffsetDateTime;
import java.util.List;

public record ErrorResponse(
        int statusCode,
        String message,
        OffsetDateTime timestamp,
        List<ValidationError> errors
) {

    public ErrorResponse(int statusCode, String message, OffsetDateTime timestamp) {
        this(statusCode, message, timestamp, List.of());
    }

    public record ValidationError(
            String field,
            String error
    ) {

    }
}
