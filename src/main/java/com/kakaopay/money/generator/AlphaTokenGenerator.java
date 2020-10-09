package com.kakaopay.money.generator;

import org.apache.commons.lang3.RandomStringUtils;

public class AlphaTokenGenerator implements TokenGenerator {

    @Override
    public String generateToken(int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }

}
