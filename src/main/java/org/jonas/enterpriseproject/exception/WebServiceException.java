package org.jonas.enterpriseproject.exception;

import java.time.OffsetDateTime;

public class WebServiceException extends RuntimeException {
    private final int statusCode;
    private final String message;
    private final OffsetDateTime timestamp;

    public WebServiceException(int statusCode, String message, OffsetDateTime timestamp) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = timestamp;
    }

    public WebServiceException(int statusCode, String message) {
        this(statusCode, message, OffsetDateTime.now());
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }
}