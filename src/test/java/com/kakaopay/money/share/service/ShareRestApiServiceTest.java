package com.kakaopay.money.share.service;

import com.kakaopay.money.constant.ShareType;
import com.kakaopay.money.share.entity.Receive;
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
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ShareRestApiServiceTest {

    @Autowired
    private ShareRestApiService shareRestApiService;

    private Share share;

    @BeforeEach
    void setUp() {
        share = Share.builder()
                .money(10000l)
                .roomId("A")
                .userId(1l)
                .count(3)
                .shareType(ShareType.EQUITY)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("뿌리기 비지니스 로직 테스트 1 : 토큰값 발행")
    void token() {
        String token = shareRestApiService.generateToken();
        Assertions.assertNotNull(token);
        Assertions.assertEquals(token.length(),3);
        Assertions.assertTrue(StringUtils.isAlpha(token));
    }

    @Test
    @DisplayName("뿌리기 비지니스 로직 테스트 2 : 돈 나누기")
    void divide() {
        long[] dividedMoney = shareRestApiService.divide(share);
        long[] expected = {3333l,3333l,3334l};
        Assertions.assertArrayEquals(dividedMoney, expected);
    }

    @Test
    @DisplayName("받기 비지니스 로직 테스트 1: 뿌린 사람이 맞으면 True, 다른 사람이면 False")
    void isSelfReceive() {
        shareRestApiService.share(this.share);
        long selfUser = share.getUserId();
        long otherUser = 99l;
        Assertions.assertTrue(shareRestApiService.isSelfReceive(share.getToken(), selfUser));
        Assertions.assertFalse(shareRestApiService.isSelfReceive(share.getToken(), otherUser));
    }

    @Test
    @DisplayName("받기 비지니스 로직 테스트 2: 뿌리기 건이 10분이 지났으면 True, 아니면 False")
    void isTimeOverToken() {
        share.setCreatedAt(LocalDateTime.now().minusMinutes(11));
        shareRestApiService.share(this.share);
        Assertions.assertTrue(shareRestApiService.isTimeOverToken(share.getToken()));

        share.setCreatedAt(LocalDateTime.now().minusMinutes(9));
        shareRestApiService.share(this.share);
        Assertions.assertFalse(shareRestApiService.isTimeOverToken(share.getToken()));
    }

    @Test
    @DisplayName("받기 비지니스 로직 테스트 3: 한 사용자가 뿌린 값을 또 가져가면 True, 아니면 False")
    void isDuplicatedUserReceive() {
        long userId = 10l;
        shareRestApiService.share(this.share);
        List<Receive> receiveList = shareRestApiService.findReceiveList(share.getToken(), share.getRoomId());

        Assertions.assertFalse(shareRestApiService.isDuplicatedUserReceive(receiveList,userId));

        Optional<Receive> receiveOptional = receiveList.stream()
                .filter(receive -> !receive.isReceived())
                .findFirst();

        receiveOptional.ifPresent(receive -> {
            receive.setToken(share.getToken());
            receive.setUserId(userId);
            receive.setReceived(true);
            shareRestApiService.receive(receive);
        });

        Assertions.assertTrue(shareRestApiService.isDuplicatedUserReceive(receiveList,userId));
    }
}