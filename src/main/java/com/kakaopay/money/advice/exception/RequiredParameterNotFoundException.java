package com.kakaopay.money.advice.exception;

public class RequiredParameterNotFoundException extends RuntimeException {
    public RequiredParameterNotFoundException() {
        super("필수 파라미터가 없습니다");
    }
}
