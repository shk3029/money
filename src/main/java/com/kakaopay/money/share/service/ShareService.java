package com.kakaopay.money.share.service;

import com.kakaopay.money.constant.ShareType;
import com.kakaopay.money.constant.TokenType;
import com.kakaopay.money.share.entity.Receive;
import com.kakaopay.money.share.repository.ReceiveRepository;
import com.kakaopay.money.share.entity.Share;
import com.kakaopay.money.share.repository.ShareRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ShareService {

    private final ShareRepositroy shareRepositroy;
    private final ReceiveRepository receiveRepository;

    @Transactional
    public Share share(Share share) {
        share.setToken(generateToken());
        Share savedShare = shareRepositroy.save(share);
        distribute(savedShare);
        return savedShare;
    }


    String generateToken() {
        return TokenType.ALPHA.getTokenGenerator().generateToken(3);
    }

    @Transactional
    void distribute(Share share) {
        long[] dividedMoney = divide(share);

        int i=0;
        for (long money : dividedMoney) {
            Receive save = receiveRepository.save(new Receive(share, i++, money));
            System.out.println(save);
        }
    }

    long[] divide(Share share) {
        return share.getShareType().getDistributer().distribute(share.getMoney(), share.getCount());
    }


}
