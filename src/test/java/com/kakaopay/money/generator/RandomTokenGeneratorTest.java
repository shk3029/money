package com.kakaopay.money.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RandomTokenGeneratorTest {

    @Test
    @DisplayName("토큰 발행 테스트")
    void generate() {
        String token = RandomTokenGenerator.generate();
        Assertions.assertNotNull(token);
        Assertions.assertEquals(token.length(), 3);
    }
}