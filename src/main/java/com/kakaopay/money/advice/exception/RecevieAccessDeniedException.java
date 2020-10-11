package com.kakaopay.money.advice.exception;

public class RecevieAccessDeniedException extends RuntimeException {
    public RecevieAccessDeniedException(String message) {
        super(message);
    }
    public RecevieAccessDeniedException() {
        super("권한이 없습니다");
    }
}
