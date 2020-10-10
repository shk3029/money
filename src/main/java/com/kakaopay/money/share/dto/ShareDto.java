package com.kakaopay.money.share.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder @Data
@NoArgsConstructor @AllArgsConstructor
public class ShareDto {
    @NotNull
    private Long money;
    @NotNull
    private Integer count;
}
