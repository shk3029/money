package com.kakaopay.money.share.dto;

import com.kakaopay.money.share.entity.Receive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder @Data
@NoArgsConstructor @AllArgsConstructor
public class SearchDto {

    private long money;
    private long receivedMoney = 0l;

    private List<ReceiveDto> receivedList = new ArrayList<>();
    private LocalDateTime createdAt;


    public void setSearchDto(Receive receive) {
        ReceiveDto receiveDto = new ReceiveDto();
        receiveDto.setUserId(receive.getUserId());
        receiveDto.setMoney(receive.getMoney());
        receivedList.add(receiveDto);
    }

    public void sumReceivedMoney() {
        receivedMoney = receivedList.stream()
                .mapToLong(ReceiveDto::getMoney)
                .sum();
    }
}
