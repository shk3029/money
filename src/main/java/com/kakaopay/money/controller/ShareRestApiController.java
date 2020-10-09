package com.kakaopay.money.controller;


import com.kakaopay.money.constant.CustomHeaders;
import com.kakaopay.money.constant.ShareType;
import com.kakaopay.money.service.ShareService;
import com.kakaopay.money.share.dto.ShareDto;
import com.kakaopay.money.share.entity.Share;
import com.kakaopay.money.share.mapper.ShareMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShareRestApiController {

    private final ShareService shareService;


    @PostMapping(value = "/api/share", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity shareMoney(
            @RequestHeader(CustomHeaders.ROOM_ID) String roomId,
            @RequestHeader(CustomHeaders.USER_ID) Long userId,
            @RequestBody ShareDto shareDto) {

        log.info(">>> {} : {} ", CustomHeaders.ROOM_ID, roomId);
        log.info(">>> {} : {} ", CustomHeaders.USER_ID, userId);

        Share share = ShareMapper.INSTANCE.shareDtoToEntity(shareDto);
        share.setShareType(ShareType.EQUITY);
        Share newShare = shareService.share(share);
        System.out.println(newShare);

        URI uri = linkTo(ShareRestApiController.class).slash(newShare.getToken()).toUri();

        CustomHeaders customHeaders = new CustomHeaders();
        customHeaders.setUserId(userId);
        customHeaders.setRoomId(roomId);
        ;

        return ResponseEntity.created(uri).headers(customHeaders).body(newShare);
    }
}














