package com.kakaopay.money.token;

import org.apache.commons.lang3.RandomStringUtils;

public class AlphaTokenGenerator implements TokenGenerator {

    private AlphaTokenGenerator() {}

    private static class LazyHolder {
        private static final AlphaTokenGenerator INSTANCE = new AlphaTokenGenerator();
    }

    public static AlphaTokenGenerator getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public String generateToken(int count) {
        return RandomStringUtils.randomAlphabetic(count);
    }

}
