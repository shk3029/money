package com.kakaopay.money.share.repository;

import com.kakaopay.money.share.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShareRepositroy extends JpaRepository<Share, String> {
    Optional<Share> findByTokenAndUserId(String id, Long userId);
}
