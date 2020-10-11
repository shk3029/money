package com.kakaopay.money.restdocs;

import com.kakaopay.money.constant.CustomHeaders;
import com.kakaopay.money.constant.ShareType;
import com.kakaopay.money.share.entity.Share;
import com.kakaopay.money.share.service.ShareService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;


public class SearchApiDocs extends CommonApiDocs {

    @Autowired
    private ShareService shareService;

    private Share share;

    @BeforeEach
    @DisplayName("Share 인스턴스 생성")
    void setShareDto() {
        Share s = new Share();
        s.setMoney(10000L);
        s.setCount(3);
        s.setUserId(1L);
        s.setRoomId("a");
        s.setShareType(ShareType.EQUITY);
        share = shareService.share(s);
    }

    @Test
    @DisplayName("조회 API REST DOCS 생성 테스트 코드")
    void search() throws Exception {
        System.out.println(share);
        this.mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/share/{token}", share.getToken())
                .accept(MediaTypes.HAL_JSON_VALUE)
                .header(CustomHeaders.ROOM_ID, "a")
                .header(CustomHeaders.USER_ID, 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(document("search",
                        preprocessRequest(
                                prettyPrint()
                        ),
                        preprocessResponse(
                                prettyPrint()
                        ),
                        links(
                                linkWithRel("self").description("self"),
                                linkWithRel("profile").description("profile")
                        ),
                        requestHeaders(
                                headerWithName(CustomHeaders.ACCEPT).description("accept header"),
                                headerWithName(CustomHeaders.CONTENT_TYPE).description("content type header"),
                                headerWithName(CustomHeaders.USER_ID).description("user_id header"),
                                headerWithName(CustomHeaders.ROOM_ID).description("room_id header")
                        ),
                        pathParameters(
                                parameterWithName("token").description("토큰")
                        ),
                        responseFields(
                                fieldWithPath("money").description("뿌린 금액"),
                                fieldWithPath("createdAt").description("뿌린 시각"),
                                fieldWithPath("receivedMoney").description("받기 완료된 금액"),
                                fieldWithPath("receivedList").description("받기 완료된 정보[사용자ID, 받은금액]"),
                                fieldWithPath("_links.self.href").description("_links.self"),
                                fieldWithPath("_links.profile.href").description("_links.profile")
                        )
                ));
    }

}
