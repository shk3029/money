package com.kakaopay.money.generator;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomTokenGenerator {

    public static String generate() {
        return RandomStringUtils.randomAlphabetic(3);
    }

}
