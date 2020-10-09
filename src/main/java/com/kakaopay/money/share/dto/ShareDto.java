package com.kakaopay.money.share.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder @Data
@NoArgsConstructor @AllArgsConstructor
public class ShareDto {

    private String token;
    private Long user_id;
    private String room_id;
    @NotNull
    private Long money;
    @NotNull
    private Integer count;
}
