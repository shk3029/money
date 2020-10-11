package com.kakaopay.money.share.controller;


import com.kakaopay.money.advice.exception.RecevieAccessDeniedException;
import com.kakaopay.money.advice.exception.RequiredParameterNotFoundException;
import com.kakaopay.money.advice.exception.TimeOverException;
import com.kakaopay.money.advice.exception.TokenNotFoundException;
import com.kakaopay.money.constant.CustomHeaders;
import com.kakaopay.money.constant.ShareType;
import com.kakaopay.money.share.dto.ReceiveDto;
import com.kakaopay.money.share.dto.SearchDto;
import com.kakaopay.money.share.dto.ShareDto;
import com.kakaopay.money.share.entity.Receive;
import com.kakaopay.money.share.entity.Share;
import com.kakaopay.money.share.mapper.ShareMapper;
import com.kakaopay.money.share.service.ShareRestApiService;
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
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequestMapping(value = "/api/share", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class ShareRestApiController {

    private final ShareRestApiService shareRestApiService;

    @PostMapping
    public ResponseEntity<EntityModel<Share>> shareMoney(
            @RequestHeader(CustomHeaders.USER_ID) Long userId,
            @RequestHeader(CustomHeaders.ROOM_ID) String roomId,
            @RequestBody @Valid ShareDto shareDto, Errors errors) {

        if (errors.hasErrors()) throw new RequiredParameterNotFoundException();

        Share share = ShareMapper.INSTANCE.shareDtoToEntity(shareDto);
        share.setUserId(userId);
        share.setRoomId(roomId);
        share.setShareType(ShareType.EQUITY);

        Share savedShare = shareRestApiService.share(share);

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

        Share share = shareRestApiService.search(token).orElse(new Share());

        if (StringUtils.isEmpty(share.getToken())) throw new TokenNotFoundException();
        if (share.getUserId().longValue() != userId) throw new RecevieAccessDeniedException();
        if (LocalDateTime.now().isAfter(share.getCreatedAt().plusDays(7))) throw new TimeOverException("7일");

        SearchDto searchDto = shareToSearchDto(share);

        return ResponseEntity
                .ok()
                .headers(getCustomHeaders(userId, roomId))
                .body(EntityModel.of(searchDto)
                        .add(linkTo(methodOn(ShareRestApiController.class).searchMoney(token, userId, roomId)).withSelfRel())
                        .add(Link.of("/docs/search/index.html#resources-search").withRel("profile")));
    }

    @PutMapping("/{token}")
    public ResponseEntity<EntityModel<ReceiveDto>> receiveMoeny(
            @PathVariable String token,
            @RequestHeader(CustomHeaders.USER_ID) Long userId,
            @RequestHeader(CustomHeaders.ROOM_ID) String roomId) {

        List<Receive> receiveList = shareRestApiService.findReceiveList(token, roomId);

        if (receiveList.isEmpty()) throw new TokenNotFoundException("토큰이 존재하지 않거나 현재 대화방이 다릅니다");
        if (shareRestApiService.isSelfReceive(token, userId)) throw new RecevieAccessDeniedException("본인이 뿌린 돈을 받을 수 없습니다");
        if (shareRestApiService.isTimeOverToken(token)) throw new TimeOverException("10분");
        if (shareRestApiService.isDuplicatedUserReceive(receiveList, userId)) throw new RecevieAccessDeniedException("중복해서 받을 수 없습니다");

        Optional<Receive> receiveOptional = receiveList.stream()
                .filter(receive -> !receive.isReceived())
                .findFirst();

        receiveOptional.ifPresent(receive -> {
            receive.setToken(token);
            receive.setUserId(userId);
            receive.setReceived(true);
            shareRestApiService.receive(receive);
        });

        ReceiveDto receiveDto = receiveToReceiveDto(receiveOptional.orElse(new Receive()));

        return ResponseEntity
                .ok()
                .headers(getCustomHeaders(userId, roomId))
                .body(EntityModel.of(receiveDto)
                    .add(linkTo(methodOn(ShareRestApiController.class).receiveMoeny(token, userId, roomId)).withSelfRel())
                    .add(Link.of("/docs/receive/index.html#resources-receive").withRel("profile")));
    }


    private CustomHeaders getCustomHeaders(Long userId, String roomId) {
        CustomHeaders customHeaders = new CustomHeaders();
        customHeaders.setUserId(userId);
        customHeaders.setRoomId(roomId);
        return customHeaders;
    }


    private SearchDto shareToSearchDto(Share share) {
        SearchDto searchDto = new SearchDto();
        searchDto.setMoney(share.getMoney());
        searchDto.setCreatedAt(share.getCreatedAt());

        share.getReceiveList().stream()
                .filter(receive -> receive.isReceived())
                .forEachOrdered(receive -> searchDto.setSearchDto(receive));

        searchDto.sumReceivedMoney();
        return searchDto;
    }

    private ReceiveDto receiveToReceiveDto(Receive receive) {
        ReceiveDto receiveDto = new ReceiveDto();
        receiveDto.setMoney(receive.getMoney());
        receiveDto.setUserId(receive.getUserId());
        return receiveDto;
    }


}














