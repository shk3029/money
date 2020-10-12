package com.kakaopay.money.share.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.money.constant.ShareType;
import com.kakaopay.money.share.entity.Share;
import com.kakaopay.money.share.service.ShareRestApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ControllerTestBasicInfo {

    final Long REQUEST_HEADER_USER_ID = 1l;
    final String REQUEST_HEADER_ROOM_ID = "a";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected Share share;

    @Autowired
    private ShareRestApiService shareRestApiService;

    @BeforeEach
    @DisplayName("뿌리기 생성")
    protected void setUp() {
        share = setShare();
    }

    protected Share createShare(Share share) {
        return shareRestApiService.share(share);
    }

    private Share setShare() {
        Share share = new Share();
        share.setMoney(10000L);
        share.setCount(3);
        share.setUserId(REQUEST_HEADER_USER_ID);
        share.setRoomId(REQUEST_HEADER_ROOM_ID);
        share.setShareType(ShareType.EQUITY);
        return share;
    }
}






