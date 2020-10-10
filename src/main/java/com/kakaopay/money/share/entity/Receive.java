package com.kakaopay.money.share.entity;

import com.kakaopay.money.share.entity.Share;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder @Getter @Setter @ToString
@RequiredArgsConstructor @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "receive_id")
@Entity
@Table(name = "receive")
public class Receive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receive_id", nullable = false)
    private Long receive_id;

    @NonNull
    @ManyToOne(targetEntity = Share.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "token")
    private Share share;

    @NonNull
    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @NonNull
    @Column(name = "money", nullable = false)
    private Long money;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "received_at")
    private LocalDateTime received_at = LocalDateTime.now();

    @Column(name = "isReceived")
    private boolean isReceived;

    public void update() {
        this.isReceived = Objects.nonNull(this.user_id);
    }


}
