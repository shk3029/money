package com.kakaopay.money.share.controller;


import com.kakaopay.money.share.Share;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/share", produces = MediaTypes.HAL_JSON_VALUE)
public class ShareController {

    @PostMapping
    public ResponseEntity shareMoney(@RequestBody Share share) {
        URI uri = linkTo(ShareController.class).slash("{token}").toUri();
        share.setToken("ABC");
        return ResponseEntity.created(uri).body(share);
    }
}
