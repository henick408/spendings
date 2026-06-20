package org.henick.spendings.exception;

public class NoSuchCategoryExistsException extends RuntimeException {
    public NoSuchCategoryExistsException(String message) {
        super(message);
    }
}
