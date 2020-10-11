package com.kakaopay.money.share.repository;

import com.kakaopay.money.share.entity.Receive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReceiveRepository extends JpaRepository<Receive, Long> {
    List<Optional<Receive>> findAllByTokenAndRoomIdOrderBySequenceAsc(String token, String roomId);
}
