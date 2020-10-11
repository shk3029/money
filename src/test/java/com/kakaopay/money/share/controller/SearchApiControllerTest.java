package com.kakaopay.money.share.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.money.constant.CustomHeaders;
import com.kakaopay.money.constant.ShareType;
import com.kakaopay.money.share.entity.Share;
import com.kakaopay.money.share.service.ShareService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SearchApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ShareService shareService;

    private Share share;

    @BeforeEach
    @DisplayName("Share 인스턴스 생성")
    void setUp() {
        share = setShare();
    }

    @Test
    @DisplayName("조회 API 200 테스트 : 성공")
    void search_200() throws Exception {
        createShare(share);

        mockMvc.perform(get("/api/share/{token}", share.getToken())
                .header(CustomHeaders.ROOM_ID, "a")
                .header(CustomHeaders.USER_ID, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("money").exists())
                .andExpect(jsonPath("receivedMoney").exists())
                .andExpect(jsonPath("receivedList").exists())
                .andExpect(jsonPath("createdAt").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists());
    }

    @Test
    @DisplayName("조회 API 404 테스트 1 : 토큰이 존재하지 않는 경우")
    void search_404_Token() throws Exception {
        createShare(share);

        String token = "XXX";

        mockMvc.perform(get("/api/share/{token}", token)
                .header(CustomHeaders.ROOM_ID, "a")
                .header(CustomHeaders.USER_ID, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("조회 API 404 테스트 2 : 뿌린 사람이 아닌 다른 사람이 조회하는 경우")
    void search_404_User() throws Exception {
        createShare(share);

        long userId = 999l;

        mockMvc.perform(get("/api/share/{token}", share.getToken())
                .header(CustomHeaders.ROOM_ID, "a")
                .header(CustomHeaders.USER_ID, userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("조회 API 404 테스트 3 : 뿌린 건이 7일이상이 지난 경우")
    void search_404_period() throws Exception {
        share.setCreatedAt(LocalDateTime.now().minusDays(8));
        createShare(share);

        mockMvc.perform(get("/api/share/{token}", share.getToken())
                .header(CustomHeaders.ROOM_ID, "a")
                .header(CustomHeaders.USER_ID, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isNotFound());
    }


    private Share createShare(Share share) {
        return shareService.share(share);
    }

    private Share setShare() {
        Share share = new Share();
        share.setMoney(10000L);
        share.setCount(3);
        share.setUserId(1L);
        share.setRoomId("a");
        share.setShareType(ShareType.EQUITY);
        return share;
    }
}






