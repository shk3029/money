package com.kakaopay.money.share.entity;

import com.kakaopay.money.constant.ShareType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder @Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(of = "token")
@Entity
@Table(name = "share")
public class Share {

    @Id
    @Column(name = "token", length = 3, nullable = false)
    private String token;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "room_id", nullable = false)
    private String roomId;

    @Column(name = "money", nullable = false)
    private Long money;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "share_type")
    @Enumerated(EnumType.STRING)
    private ShareType shareType;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "token")
    @OrderBy("sequence asc")
    private List<Receive> receiveList;

    public List<Receive> getReceiveList() {
        if (Objects.isNull(receiveList)) {
            receiveList = new ArrayList<>();
        }
        return receiveList;
    }
}
