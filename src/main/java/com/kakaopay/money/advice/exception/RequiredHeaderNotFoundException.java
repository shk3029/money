package com.kakaopay.money.advice.exception;

public class RequiredHeaderNotFoundException extends RuntimeException {
    public RequiredHeaderNotFoundException(String message) {
        super(message);
    }
}
