package com.kakaopay.money.share.controller;

import com.kakaopay.money.constant.CustomHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SearchApiControllerTest extends ControllerTestBasicInfo {

    @Test
    @DisplayName("조회 API 200 테스트 : 성공")
    void search_200() throws Exception {
        createShare(share);

        mockMvc.perform(get("/api/share/{token}", share.getToken())
                .header(CustomHeaders.ROOM_ID, REQUEST_HEADER_ROOM_ID)
                .header(CustomHeaders.USER_ID, REQUEST_HEADER_USER_ID)
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
                .header(CustomHeaders.ROOM_ID, REQUEST_HEADER_ROOM_ID)
                .header(CustomHeaders.USER_ID, REQUEST_HEADER_USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("조회 API 404 테스트 2 : 뿌린 건이 7일이상이 지난 경우")
    void search_404_period() throws Exception {
        share.setCreatedAt(LocalDateTime.now().minusDays(8));
        createShare(share);

        mockMvc.perform(get("/api/share/{token}", share.getToken())
                .header(CustomHeaders.ROOM_ID, REQUEST_HEADER_ROOM_ID)
                .header(CustomHeaders.USER_ID, REQUEST_HEADER_USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("조회 API 403 권한 테스트 : 뿌린 사람이 아닌 다른 사람이 조회하는 경우")
    void search_403_User() throws Exception {
        createShare(share);

        long otherUserId = 999l;

        mockMvc.perform(get("/api/share/{token}", share.getToken())
                .header(CustomHeaders.ROOM_ID, REQUEST_HEADER_ROOM_ID)
                .header(CustomHeaders.USER_ID, otherUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isForbidden());
    }

}






