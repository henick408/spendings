package org.henick.spendings.exception;

public record ErrorResponse(
        int statusCode,
        String message
) {
}
