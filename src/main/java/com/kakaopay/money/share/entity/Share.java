package com.kakaopay.money.share.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder @Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(of = "token")
@Entity
public class Share {

    @Id
    private String token;

    private Long user_id;

    private String room_id;

    private Long money;

    private Integer count;

    private LocalDateTime created_at = LocalDateTime.now();
}
