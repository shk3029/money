package com.kakaopay.money;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class MoneyApplicationTests {

    static final Long REQUEST_HEADER_USER_ID = 1l;
    static final String REQUEST_HEADER_ROOM_ID = "a";
    
    @Test
    void contextLoads() {
    }

}
