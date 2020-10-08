package com.kakaopay.money.sharing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.money.share.Share;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
class ShareControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void share() throws Exception {
        Share share = Share.builder()
                .token("ABC")
                .room_id("A")
                .user_id(1L)
                .money(10000L)
                .count(3)
                .build();

        mockMvc.perform(post("/api/share/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(share)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("token").exists());
    }


}