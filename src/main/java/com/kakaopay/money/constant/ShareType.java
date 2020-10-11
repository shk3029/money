package com.kakaopay.money.constant;

import com.kakaopay.money.distributor.Distributer;
import com.kakaopay.money.distributor.EquityDistributer;
import com.kakaopay.money.distributor.RandomDistributer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShareType {

    FIRST("선착순", EquityDistributer.getInstance()),
    EQUITY("1/N", EquityDistributer.getInstance()),
    RANDOM("랜덤", RandomDistributer.getInstance());

    private String description;
    private Distributer distributer;
}
