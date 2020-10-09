package com.kakaopay.money.share.repository;

import com.kakaopay.money.share.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareRepositroy extends JpaRepository<Share, String> {
}
