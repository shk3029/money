package com.kakaopay.money.generator;

import com.kakaopay.money.constant.TokenType;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TokenGeneratorTest {

    TokenGenerator tokenGenerator;

    @Test
    @DisplayName("영문 토큰 발행 테스트 (토크 생성, 길이, 영문 확인)")
    void generateAlphabeticToken() {
        tokenGenerator = TokenType.ALPHA.getTokenGenerator();
        String token = tokenGenerator.generateToken(3);

        Assertions.assertNotNull(token);
        Assertions.assertEquals(token.length(),3);
        Assertions.assertTrue(StringUtils.isAlpha(token));
    }

    @Test
    @DisplayName("숫자 토큰 발행 테스트 (토크 생성, 길이, 숫자 확인)")
    void generateNumericToken() {
        tokenGenerator = TokenType.NUMBER.getTokenGenerator();
        String token = tokenGenerator.generateToken(3);

        Assertions.assertNotNull(token);
        Assertions.assertEquals(token.length(), 3);
        Assertions.assertTrue(StringUtils.isNumeric(token));
    }

}