package com.kakaopay.money.share.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.money.constant.CustomHeaders;
import com.kakaopay.money.share.dto.ShareDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ShareApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("뿌리기 API CREATED(201) 테스트")
    void share_201() throws Exception {

        ShareDto shareDto = createShareDto();

        mockMvc.perform(post("/api/share")
                .header(CustomHeaders.ROOM_ID, "a")
                .header(CustomHeaders.USER_ID, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(shareDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("token").exists())
                .andExpect(header().exists(CustomHeaders.ROOM_ID))
                .andExpect(header().exists(CustomHeaders.USER_ID))
                .andExpect(header().exists(CustomHeaders.LOCATION))
                .andExpect(header().string(CustomHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.share").exists())
                .andExpect(jsonPath("_links.search").exists())
                .andExpect(jsonPath("_links.profile").exists());
    }

    @Test
    @DisplayName("뿌리기 API BAD_REQUEST(400) - Empty Input 테스트")
    void share_400_empty_input() throws Exception {

        ShareDto shareDto = ShareDto.builder().build();

        mockMvc.perform(post("/api/share")
                .header(CustomHeaders.ROOM_ID, "a")
                .header(CustomHeaders.USER_ID, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(shareDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("뿌리기 API BAD_REQUEST(400) - Missing Header 테스트")
    void share_400_missing_header() throws Exception {

        ShareDto shareDto = createShareDto();

        mockMvc.perform(post("/api/share")
                .header(CustomHeaders.USER_ID, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(shareDto)))
                .andExpect(status().isBadRequest());

    }

    private ShareDto createShareDto() {
        return ShareDto.builder()
                .money(10000L)
                .count(3)
                .build();
    }
}