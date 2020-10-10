package com.kakaopay.money.service;

import com.kakaopay.money.receive.respository.ReceiveRepository;
import com.kakaopay.money.share.entity.Share;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ShareServiceTest {

    @Autowired
    private ShareService shareService;

    @Autowired
    private ReceiveRepository receiveRepository;

    private Share shareA;
    private Share shareB;

    @BeforeEach
    void setUp() {
        shareA = Share.builder()
                .money(10000l)
                .room_id("A")
                .user_id(1l)
                .count(3)
                .created_at(LocalDateTime.now())
                .build();

        shareB = Share.builder()
                .money(100000l)
                .room_id("AD")
                .user_id(3l)
                .count(4)
                .created_at(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("API 뿌리기 비지니스 로직 테스트 1 : 토큰값 발행")
    void token() {
        String token = shareService.generateToken();
        Assertions.assertNotNull(token);
        Assertions.assertEquals(token.length(),3);
        Assertions.assertTrue(StringUtils.isAlpha(token));
    }

    @Test
    @DisplayName("API 뿌리기 비지니스 로직 테스트 2 : 돈 나누기")
    void divide() {
        long[] dividedMoney = shareService.divide(shareA);
        long[] expected = {3333l,3333l,3334l};
        Assertions.assertArrayEquals(dividedMoney, expected);
    }

    @Test
    @DisplayName("API 뿌리기 비지니스 로직 통합 테스트")
    void distributor() {
        Share share = shareService.share(this.shareA);
        Share share2 = shareService.share(this.shareB);
        receiveRepository.findAll().forEach(x-> System.out.println(x));
    }
}