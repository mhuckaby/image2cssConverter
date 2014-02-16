package com.rf1m.image2css.domain;

public class ErrorResponse {
    protected final String message;

    public ErrorResponse(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
