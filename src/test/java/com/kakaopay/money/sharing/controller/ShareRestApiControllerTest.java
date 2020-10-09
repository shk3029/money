package com.kakaopay.money.sharing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.money.constant.CustomHeaders;
import com.kakaopay.money.constant.ShareType;
import com.kakaopay.money.constant.TokenType;
import com.kakaopay.money.share.entity.Share;
import com.kakaopay.money.share.repository.ShareRepositroy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class ShareRestApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("뿌리기 API Controller Test")
    void share() throws Exception {
        Share share = Share.builder()
                .room_id("A")
                .user_id(1L)
                .money(10000L)
                .count(3)
                .shareType(ShareType.EQUITY)
                .created_at(LocalDateTime.now())
                .build();

        mockMvc.perform(post("/api/share/")
                .header(CustomHeaders.ROOM_ID, "a")
                .header(CustomHeaders.USER_ID, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(share)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("token").exists())
                .andExpect(header().exists(CustomHeaders.ROOM_ID))
                .andExpect(header().exists(CustomHeaders.USER_ID))
                .andExpect(header().exists(CustomHeaders.LOCATION))
                .andExpect(header().string(CustomHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
        ;
    }


}