package com.kakaopay.money.restdocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.money.constant.ShareType;
import com.kakaopay.money.share.entity.Share;
import com.kakaopay.money.share.service.ShareRestApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class CommonApiDocs {

    final Long REQUEST_HEADER_USER_ID = 1l;
    final String REQUEST_HEADER_ROOM_ID = "a";

    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected ShareRestApiService shareRestApiService;

    protected Share share;

    protected void saveShare() {
        Share s = new Share();
        s.setMoney(10000L);
        s.setCount(3);
        s.setUserId(1L);
        s.setRoomId("a");
        s.setShareType(ShareType.EQUITY);
        share = shareRestApiService.share(s);
    }

    @BeforeEach
    @DisplayName("mockMvc 초기세팅")
    void setUp(WebApplicationContext webApplicationContext,
               RestDocumentationContextProvider restDocumentationContextProvider) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(
                        documentationConfiguration(restDocumentationContextProvider)
                                .operationPreprocessors()
                ).build();
    }
}
