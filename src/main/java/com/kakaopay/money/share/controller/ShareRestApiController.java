package com.kakaopay.money.share.controller;


import com.kakaopay.money.constant.CustomHeaders;
import com.kakaopay.money.share.dto.ShareDto;
import com.kakaopay.money.share.entity.Share;
import com.kakaopay.money.share.mapper.ShareMapper;
import com.kakaopay.money.share.service.ShareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShareRestApiController {

    private final ShareService shareService;

    @PostMapping(value = "/api/share", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Share>> shareMoney(
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

        return ResponseEntity
                .created(linkTo(ShareRestApiController.class).slash(savedShare.getToken()).toUri())
                .headers(getCustomHeaders(userId,roomId))
                .body(EntityModel.of(savedShare)
                        .add(linkTo(methodOn(ShareRestApiController.class)
                                .shareMoney(userId, roomId, shareDto, errors)).withSelfRel())
                        .add(linkTo(methodOn(ShareRestApiController.class)
                                .shareMoney(userId, roomId, shareDto, errors)).slash(savedShare.getToken()).withRel("share"))
                        .add(linkTo(methodOn(ShareRestApiController.class)
                                .shareMoney(userId, roomId, shareDto, errors)).slash(savedShare.getToken()).withRel("search")));
    }




    private CustomHeaders getCustomHeaders(Long userId, String roomId) {
        CustomHeaders customHeaders = new CustomHeaders();
        customHeaders.setUserId(userId);
        customHeaders.setRoomId(roomId);
        return customHeaders;
    }
}














