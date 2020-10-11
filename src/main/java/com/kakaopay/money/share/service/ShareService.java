package com.kakaopay.money.share.service;

import com.kakaopay.money.constant.ShareType;
import com.kakaopay.money.constant.TokenType;
import com.kakaopay.money.share.dto.SearchDto;
import com.kakaopay.money.share.entity.Receive;
import com.kakaopay.money.share.repository.ReceiveRepository;
import com.kakaopay.money.share.entity.Share;
import com.kakaopay.money.share.repository.ShareRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ShareService {

    private final ShareRepositroy shareRepositroy;
    private final ReceiveRepository receiveRepository;

    @Transactional
    public Share share(Share share) {
        share.setToken(generateToken());
        Share savedShare = shareRepositroy.save(share);
        long[] dividedMoney = divide(savedShare);

        int i=0;
        for (long money : dividedMoney) {
            Receive save = receiveRepository.save(new Receive(savedShare.getToken(), i++, money));
            System.out.println(save);
        }
        return savedShare;
    }

    @Transactional
    public Optional<Share> search(String token) {
        return shareRepositroy.findById(token);
    }

    public SearchDto shareToSearchDto(Share share) {
        SearchDto searchDto = new SearchDto();
        searchDto.setMoney(share.getMoney());
        searchDto.setCreatedAt(share.getCreatedAt());

        share.getReceiveList().stream()
                .filter(receive -> receive.isReceived())
                .forEachOrdered(receive -> searchDto.setSearchDto(receive));

        searchDto.sumReceivedMoney();
        return searchDto;
    }


    String generateToken() {
        return TokenType.ALPHA.getTokenGenerator().generateToken(3);
    }

    long[] divide(Share share) {
        return share.getShareType().getDistributer().distribute(share.getMoney(), share.getCount());
    }


}
