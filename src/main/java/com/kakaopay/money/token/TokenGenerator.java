package com.kakaopay.money.token;

public interface TokenGenerator {

    /**
     * Token 생성 방식을 구현한다
     * @param count (토큰 생성 길이)
     * @return
     */
    String generateToken(int count);
}
