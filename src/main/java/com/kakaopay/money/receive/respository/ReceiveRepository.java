package com.kakaopay.money.receive.respository;

import com.kakaopay.money.receive.entity.Receive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiveRepository extends JpaRepository<Receive, Long> {
}
