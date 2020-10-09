package com.kakaopay.money.constant;

import com.kakaopay.money.generator.AlphaTokenGenerator;
import com.kakaopay.money.generator.NumericTokenGenerator;
import com.kakaopay.money.generator.TokenGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenType {
    ALPHA("영문토큰", new AlphaTokenGenerator()),
    NUMBER("숫자토큰", new NumericTokenGenerator());

    private String description;
    private TokenGenerator tokenGenerator;
}
