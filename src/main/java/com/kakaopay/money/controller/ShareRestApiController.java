package com.kakaopay.money.controller;


import com.kakaopay.money.constant.CustomHeaders;
import com.kakaopay.money.service.ShareService;
import com.kakaopay.money.share.dto.ShareDto;
import com.kakaopay.money.share.entity.Share;
import com.kakaopay.money.share.mapper.ShareMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShareRestApiController {

    private final ShareService shareService;

    @PostMapping(value = "/api/share", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity shareMoney(
            @RequestHeader(CustomHeaders.USER_ID) Long userId,
            @RequestHeader(CustomHeaders.ROOM_ID) String roomId,
            @RequestBody @Valid ShareDto shareDto, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Share share = ShareMapper.INSTANCE.shareDtoToEntity(shareDto);
        share.setUser_id(userId);
        share.setRoom_id(roomId);

        Share savedShare = shareService.share(share);
        URI uri = linkTo(ShareRestApiController.class).slash(savedShare.getToken()).toUri();

        return ResponseEntity.created(uri).headers(customHeaders(userId,roomId)).body(savedShare);
    }



    private CustomHeaders customHeaders(Long userId, String roomId) {
        CustomHeaders customHeaders = new CustomHeaders();
        customHeaders.setUserId(userId);
        customHeaders.setRoomId(roomId);
        return customHeaders;
    }
}














