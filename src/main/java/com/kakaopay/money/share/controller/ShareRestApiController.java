package com.kakaopay.money.share.controller;


import com.kakaopay.money.constant.CustomHeaders;
import com.kakaopay.money.constant.ShareType;
import com.kakaopay.money.share.dto.SearchDto;
import com.kakaopay.money.share.dto.ShareDto;
import com.kakaopay.money.share.entity.Receive;
import com.kakaopay.money.share.entity.Share;
import com.kakaopay.money.share.mapper.ShareMapper;
import com.kakaopay.money.share.service.ShareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping(value = "/api/share", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class ShareRestApiController {

    private final ShareService shareService;

    @PostMapping
    public ResponseEntity<EntityModel<Share>> shareMoney(
            @RequestHeader(CustomHeaders.USER_ID) Long userId,
            @RequestHeader(CustomHeaders.ROOM_ID) String roomId,
            @RequestBody @Valid ShareDto shareDto, Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Share share = ShareMapper.INSTANCE.shareDtoToEntity(shareDto);
        share.setUserId(userId);
        share.setRoomId(roomId);
        share.setShareType(ShareType.EQUITY);

        Share savedShare = shareService.share(share);

        return ResponseEntity
                .created(linkTo(ShareRestApiController.class).slash(savedShare.getToken()).toUri())
                .headers(getCustomHeaders(userId, roomId))
                .body(EntityModel.of(savedShare)
                        .add(linkTo(methodOn(ShareRestApiController.class).shareMoney(userId, roomId, shareDto, errors)).withSelfRel())
                        .add(linkTo(methodOn(ShareRestApiController.class).shareMoney(userId, roomId, shareDto, errors)).slash(savedShare.getToken()).withRel("share"))
                        .add(linkTo(methodOn(ShareRestApiController.class).searchMoney(share.getToken(), userId, roomId)).slash(savedShare.getToken()).withRel("search"))
                        .add(Link.of("/docs/share/index.html#resources-share").withRel("profile")));
    }

    @GetMapping("/{token}")
    public ResponseEntity<EntityModel<SearchDto>> searchMoney(
            @PathVariable String token,
            @RequestHeader(CustomHeaders.USER_ID) Long userId,
            @RequestHeader(CustomHeaders.ROOM_ID) String roomId) {

        Share share = shareService.search(token).orElse(new Share());

        if (StringUtils.isEmpty(share.getToken())) {
            return ResponseEntity.notFound().build();
        }

        if (share.getUserId().longValue() != userId) {
            return ResponseEntity.notFound().build();
        }

        if (LocalDateTime.now().isAfter(share.getCreatedAt().plusDays(7))) {
            return ResponseEntity.notFound().build();
        }

        SearchDto searchDto = shareService.shareToSearchDto(share);

        return ResponseEntity
                .ok()
                .headers(getCustomHeaders(userId, roomId))
                .body(EntityModel.of(searchDto)
                        .add(linkTo(methodOn(ShareRestApiController.class).searchMoney(token, userId, roomId)).withSelfRel())
                        .add(Link.of("/docs/search/index.html#resources-search").withRel("profile")));
    }

    @PutMapping("/{token}")
    public  ResponseEntity<EntityModel<Receive>> receiveMoeny(
            @PathVariable String token,
            @RequestHeader(CustomHeaders.USER_ID) Long userId,
            @RequestHeader(CustomHeaders.ROOM_ID) String roomId) {
        return ResponseEntity
                .ok(EntityModel.of(new Receive()));
    }




    private CustomHeaders getCustomHeaders(Long userId, String roomId) {
        CustomHeaders customHeaders = new CustomHeaders();
        customHeaders.setUserId(userId);
        customHeaders.setRoomId(roomId);
        return customHeaders;
    }
}














