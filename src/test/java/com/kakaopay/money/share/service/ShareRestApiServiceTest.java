package com.kakaopay.money.share.service;

import com.kakaopay.money.constant.ShareType;
import com.kakaopay.money.share.entity.Receive;
import com.kakaopay.money.share.repository.ReceiveRepository;
import com.kakaopay.money.share.entity.Share;
import com.kakaopay.money.share.repository.ShareRepositroy;
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
import java.util.stream.Collectors;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ShareRestApiServiceTest {

    @Autowired
    private ShareRestApiService shareRestApiService;

    @Autowired
    private ReceiveRepository receiveRepository;

    @Autowired
    private ShareRepositroy shareRepositroy;

    private Share shareA;
    private Share shareB;

    @BeforeEach
    void setUp() {
        shareA = Share.builder()
                .money(10000l)
                .roomId("A")
                .userId(1l)
                .count(3)
                .shareType(ShareType.EQUITY)
                .createdAt(LocalDateTime.now())
                .build();

        shareB = Share.builder()
                .money(100000l)
                .roomId("AD")
                .userId(3l)
                .count(4)
                .shareType(ShareType.EQUITY)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("API 뿌리기 비지니스 로직 테스트 1 : 토큰값 발행")
    void token() {
        String token = shareRestApiService.generateToken();
        Assertions.assertNotNull(token);
        Assertions.assertEquals(token.length(),3);
        Assertions.assertTrue(StringUtils.isAlpha(token));
    }

    @Test
    @DisplayName("API 뿌리기 비지니스 로직 테스트 2 : 돈 나누기")
    void divide() {
        long[] dividedMoney = shareRestApiService.divide(shareA);
        long[] expected = {3333l,3333l,3334l};
        Assertions.assertArrayEquals(dividedMoney, expected);
    }

    @Test
    @DisplayName("API 뿌리기 비지니스 로직 테스트 3 : 2건의 뿌리기 요청과 7건의 분배")
    void distributor() {
        shareRestApiService.share(this.shareA);
        shareRestApiService.share(this.shareB);

        List<Share> shareList = shareRepositroy.findAll();
        List<Receive> recevieList = receiveRepository.findAll();

        System.out.println(recevieList);
        Assertions.assertEquals(shareList.size(), 2);
        Assertions.assertEquals(recevieList.size(), 7);
    }

    @Test
    @DisplayName("API 받기 비지니스 로직 테스트")
    void receive() {
        Share share = shareRestApiService.share(this.shareA);
        Long headerUserId = 1000l;

        // 같은 방, 같은 토큰의 뿌리기 건수를 시퀀스 순서에 맞춰서 가져온다
        List<Optional<Receive>> receiveOptionalList = receiveRepository.findAllByTokenAndRoomIdOrderBySequenceAsc(share.getToken(), share.getRoomId());

        List<Receive> receiveList = receiveOptionalList.stream()
                .filter(receiveOpt -> receiveOpt.isPresent())
                .map(receiveOpt -> receiveOpt.get()).collect(Collectors.toList());


        // 자신이 뿌린거 받을려고 할때 익셉션
        Optional<Share> byIdAndUserId = shareRepositroy.findByTokenAndUserId(share.getToken(), headerUserId);
        if (byIdAndUserId.isPresent()) throw new RuntimeException();


        // 한번받은 뿌리기는 받을 수 없다
        boolean isDuplicatedUser = receiveList.stream()
                .filter(receive -> receive.isReceived())
                .map(receive -> receive.getUserId())
                .anyMatch(userId -> headerUserId.equals(userId));
        if (isDuplicatedUser) throw new RuntimeException();


        // 10분이 지나지 않은 건수만 가져온다
        Optional<Receive> firstReceive = receiveList.stream()
                .filter(receive -> !receive.isReceived())
                .findFirst();

        firstReceive.ifPresent(x-> System.out.println(x));

    }
}