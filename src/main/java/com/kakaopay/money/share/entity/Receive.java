package com.kakaopay.money.share.entity;

import lombok.*;

import javax.persistence.*;

@Builder @Getter @Setter @ToString
@RequiredArgsConstructor @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "receiveId")
@Entity
@Table(name = "receive")
public class Receive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receive_id", nullable = false)
    private Long receiveId;

    @Column(name = "token")
    @NonNull
    private String token;

    @NonNull
    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @NonNull
    @Column(name = "money", nullable = false)
    private Long money;

    @Column(name = "user_id")
    private Long userId;

    @NonNull
    @Column(name = "room_id")
    private String roomId;

    @Column(name = "is_received")
    private boolean isReceived;
}
