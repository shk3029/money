package com.kakaopay.money.service;

import com.kakaopay.money.constant.ShareType;
import com.kakaopay.money.constant.TokenType;
import com.kakaopay.money.share.entity.Share;
import com.kakaopay.money.share.repository.ShareRepositroy;
import com.kakaopay.money.distributor.Distributer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ShareService {

    private final ShareRepositroy shareRepositroy;


    @Transactional
    public Share share(Share share) {
        share.setToken(generateToken());
        return shareRepositroy.save(share);
    }


    // 3개짜리 영문 토큰값 발급
    private String generateToken() {
        return TokenType.ALPHA.getTokenGenerator().generateToken(3);
    }

    // 분배
    private void distribute(Share share) {
        Distributer equityDistributer = share.getShareType().getDistributer();
        long[] distributed = equityDistributer.distribute(share.getMoney(), share.getCount());

        for (int i=0; i<distributed.length; i++) {

        }
    }
}
