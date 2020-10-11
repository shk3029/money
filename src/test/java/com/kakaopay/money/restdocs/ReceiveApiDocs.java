package com.kakaopay.money.restdocs;

import com.kakaopay.money.constant.CustomHeaders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

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

public class ReceiveApiDocs extends CommonApiDocs {

    @Test
    @DisplayName("받기 API REST DOCS 생성 테스트 코드")
    void receive() throws Exception {
        saveShare();
        long receiveUserId = 2l;

        this.mockMvc.perform(
                RestDocumentationRequestBuilders.put("/api/share/{token}", share.getToken())
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .header(CustomHeaders.ROOM_ID, REQUEST_HEADER_ROOM_ID)
                        .header(CustomHeaders.USER_ID, receiveUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(document("receive",
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
                                fieldWithPath("userId").description("사용자 id"),
                                fieldWithPath("money").description("받은 금액"),
                                fieldWithPath("_links.self.href").description("_links.self"),
                                fieldWithPath("_links.profile.href").description("_links.profile")
                        )
                ));
    }
}
