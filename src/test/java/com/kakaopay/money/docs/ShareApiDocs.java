package com.kakaopay.money.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.money.constant.CustomHeaders;
import com.kakaopay.money.share.dto.ShareDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class ShareApiDocs extends CommonApiDocs {

    @Autowired
    private ObjectMapper objectMapper;

    private ShareDto shareDto;

    @BeforeEach
    @DisplayName("ShareDto 초기 셋팅")
    void setShareDto() {
        shareDto = ShareDto.builder()
                .money(10000L)
                .count(3)
                .build();
    }

    @Test
    @DisplayName("뿌리기 API REST DOCS 생성 테스트 코드")
    void share() throws Exception {
        this.mockMvc.perform(post("/api/share")
                .header(CustomHeaders.ROOM_ID, "a")
                .header(CustomHeaders.USER_ID, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(shareDto)))
                .andDo(document("share",
                        preprocessRequest(
                                prettyPrint()
                        ),
                        preprocessResponse(
                                prettyPrint()
                        ),
                        links(
                                linkWithRel("self").description("self"),
                                linkWithRel("share").description("뿌리기"),
                                linkWithRel("search").description("조회"),
                                linkWithRel("profile").description("profile")
                        ),
                        requestHeaders(
                                headerWithName(CustomHeaders.ACCEPT).description("accept header"),
                                headerWithName(CustomHeaders.CONTENT_TYPE).description("content type header"),
                                headerWithName(CustomHeaders.USER_ID).description("user_id header"),
                                headerWithName(CustomHeaders.ROOM_ID).description("room_id header")
                        ),
                        requestFields(
                                fieldWithPath("money").description("금액"),
                                fieldWithPath("count").description("인원")

                        ),
                        responseHeaders(
                                headerWithName(CustomHeaders.LOCATION).description("location header"),
                                headerWithName(CustomHeaders.CONTENT_TYPE).description("content type header")
                        ),
                        responseFields(
                                fieldWithPath("token").description("고유한 토큰값 (영문 3자리)"),
                                fieldWithPath("user_id").description("사용자 id"),
                                fieldWithPath("room_id").description("대화방 id"),
                                fieldWithPath("money").description("금액"),
                                fieldWithPath("count").description("인원"),
                                fieldWithPath("shareType").description("뿌리는 타입"),
                                fieldWithPath("receiveList").description("받은 사람들"),
                                fieldWithPath("created_at").description("뿌린 날짜"),
                                fieldWithPath("_links.self.href").description("_links.self"),
                                fieldWithPath("_links.share.href").description("_links.share.href"),
                                fieldWithPath("_links.search.href").description("_links.search.href"),
                                fieldWithPath("_links.profile.href").description("_links.profile")
                        )
                ));
    }
}
