package com.kakaopay.money.advice.exception;

public class TimeOverException extends RuntimeException {
    public TimeOverException(String message) {
        super("기간이 만료되었습니다("+message+")");
    }
}
