package org.henick.spendings.exception;

public class NoSuchExpenseExistsException extends RuntimeException {
    public NoSuchExpenseExistsException(String message) {
        super(message);
    }
}
