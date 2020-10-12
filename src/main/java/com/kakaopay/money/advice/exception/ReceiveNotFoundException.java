package com.kakaopay.money.advice.exception;

public class ReceiveNotFoundException extends RuntimeException {
    public ReceiveNotFoundException(String message) {
        super(message);
    }

    public ReceiveNotFoundException() {
        super("뿌린돈을 모두 받아갔습니다");
    }
}
