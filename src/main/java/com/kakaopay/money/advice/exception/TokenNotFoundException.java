package com.kakaopay.money.advice.exception;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String message) {
        super(message);
    }

    public TokenNotFoundException() {
        super("토큰이 존재하지 않습니다");
    }
}
