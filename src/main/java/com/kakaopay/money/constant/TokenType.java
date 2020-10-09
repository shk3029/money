package com.kakaopay.money.constant;

import com.kakaopay.money.token.AlphaTokenGenerator;
import com.kakaopay.money.token.NumericTokenGenerator;
import com.kakaopay.money.token.TokenGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenType {

    ALPHA("영문토큰", AlphaTokenGenerator.getInstance()),
    NUMBER("숫자토큰", NumericTokenGenerator.getInstance());

    private String description;
    private TokenGenerator tokenGenerator;
}
