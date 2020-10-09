package com.kakaopay.money.share.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @Data
@NoArgsConstructor @AllArgsConstructor
public class ShareDto {

    private String token;
    private Long user_id;
    private String room_id;
    private Long money;
    private Integer count;
}
