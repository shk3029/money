package com.kakaopay.money.share;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShareTest {

    @Test
    @DisplayName("Share 객체 생성 테스트")
    void create() {
        Share share = Share.builder()
                .token("Cab")
                .room_id("K")
                .user_id(1L)
                .money(10000L)
                .count(3)
                .build();

        Assertions.assertNotNull(share);
    }

}