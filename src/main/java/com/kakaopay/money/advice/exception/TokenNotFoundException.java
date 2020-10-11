package com.kakaopay.money.advice.exception;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String message) {
        super("Token이 존재하지 않습니다");
    }
}
