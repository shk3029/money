package com.kakaopay.money.share.controller;

import com.kakaopay.money.constant.CustomHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReceiveApiControllerTest extends ControllerTestBasicInfo {

    final long RECEIVE_USER_ID = 2l;

    @Test
    @DisplayName("받기 API 200 테스트 : 성공 (사용자 1l이 뿌린 돈을 사용자 2l이 받는 경우)")
    void receive_200() throws Exception {
        createShare(share);

        mockMvc.perform(put("/api/share/{token}", share.getToken())
                .header(CustomHeaders.USER_ID, RECEIVE_USER_ID)
                .header(CustomHeaders.ROOM_ID, REQUEST_HEADER_ROOM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("받기 API 404 테스트 1 : 토큰이 존재하지 않는 경우")
    void receive_404_token() throws Exception {
        createShare(share);
        share.setToken("XXI");

        mockMvc.perform(put("/api/share/{token}", share.getToken())
                .header(CustomHeaders.USER_ID, RECEIVE_USER_ID)
                .header(CustomHeaders.ROOM_ID, REQUEST_HEADER_ROOM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("받기 API 404 테스트 2 : 현재 대화방이 다른 경우")
    void receive_404_roomId() throws Exception {
        createShare(share);
        String receiveRoomId = "BBB";

        mockMvc.perform(put("/api/share/{token}", share.getToken())
                .header(CustomHeaders.USER_ID, RECEIVE_USER_ID)
                .header(CustomHeaders.ROOM_ID, receiveRoomId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("받기 API 404 테스트 3 : 뿌리고 10분이 지나고 받기를 요청한 경우")
    void receive_400_time_over() throws Exception {
        share.setCreatedAt(LocalDateTime.now().minusMinutes(11));
        createShare(share);

        mockMvc.perform(put("/api/share/{token}", share.getToken())
                .header(CustomHeaders.USER_ID, RECEIVE_USER_ID)
                .header(CustomHeaders.ROOM_ID, REQUEST_HEADER_ROOM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("받기 API 403 권한 테스트 : 자신이 뿌린 돈을 받으려고 하는 경우")
    void receive_403_self() throws Exception {
        createShare(share);

        mockMvc.perform(put("/api/share/{token}", share.getToken())
                .header(CustomHeaders.USER_ID, share.getUserId())
                .header(CustomHeaders.ROOM_ID, REQUEST_HEADER_ROOM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("받기 API 403 권한 테스트 : 이미 받은 사용자가 다시 받기를 요청한 경우 (요청1 : 200, 요청2: 400)")
    void receive_403_duplicatedUser() throws Exception {
        createShare(share);

        mockMvc.perform(put("/api/share/{token}", share.getToken())
                .header(CustomHeaders.USER_ID, RECEIVE_USER_ID)
                .header(CustomHeaders.ROOM_ID, REQUEST_HEADER_ROOM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());


        mockMvc.perform(put("/api/share/{token}", share.getToken())
                .header(CustomHeaders.USER_ID, RECEIVE_USER_ID)
                .header(CustomHeaders.ROOM_ID, REQUEST_HEADER_ROOM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}






