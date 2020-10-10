package com.kakaopay.money.service;

import com.kakaopay.money.constant.ShareType;
import com.kakaopay.money.constant.TokenType;
import com.kakaopay.money.receive.entity.Receive;
import com.kakaopay.money.receive.respository.ReceiveRepository;
import com.kakaopay.money.share.entity.Share;
import com.kakaopay.money.share.repository.ShareRepositroy;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ShareService {

    private final ShareRepositroy shareRepositroy;
    private final ReceiveRepository receiveRepository;

    @Transactional
    public Share share(Share share) {
        share.setToken(generateToken());
        Share savedShare = shareRepositroy.save(share);
        System.out.println(savedShare);
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
        return ShareType.EQUITY.getDistributer().distribute(share.getMoney(), share.getCount());
    }


}
