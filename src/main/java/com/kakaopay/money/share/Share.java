package com.kakaopay.money.share;

import com.kakaopay.money.constant.ShareType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Share {

    @Id
    private String token;

    private Long user_id;
    private String room_id;
    private Long money;
    private int count;

    @Enumerated(EnumType.STRING)
    private ShareType shareType;
}
