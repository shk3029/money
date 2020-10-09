package com.kakaopay.money.token;

import org.apache.commons.lang3.RandomStringUtils;

public class NumericTokenGenerator implements TokenGenerator {

    private NumericTokenGenerator() {}

    private static class LazyHolder {
        private static final NumericTokenGenerator INSTANCE = new NumericTokenGenerator();
    }

    public static NumericTokenGenerator getInstance() {
        return NumericTokenGenerator.LazyHolder.INSTANCE;
    }

    @Override
    public String generateToken(int count) {
        return RandomStringUtils.randomNumeric(count);
    }
}
