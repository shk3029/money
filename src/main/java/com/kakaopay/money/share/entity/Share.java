package com.kakaopay.money.share.entity;

import com.kakaopay.money.constant.ShareType;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder @Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(of = "token")
@Entity
public class Share {

    @Id
    @NotNull
    private String token;

    @NotNull
    private Long user_id;

    @NotNull
    private String room_id;

    @NotNull
    private Long money;

    @NotNull
    private Integer count;

    @Enumerated(EnumType.STRING)
    private ShareType shareType;

    private LocalDateTime created_at = LocalDateTime.now();

}
