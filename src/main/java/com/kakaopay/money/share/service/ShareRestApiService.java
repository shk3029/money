package com.kakaopay.money.share.service;

import com.kakaopay.money.constant.TokenType;
import com.kakaopay.money.share.entity.Receive;
import com.kakaopay.money.share.entity.Share;
import com.kakaopay.money.share.repository.ReceiveRepository;
import com.kakaopay.money.share.repository.ShareRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ShareRestApiService {

    private final ShareRepositroy shareRepositroy;
    private final ReceiveRepository receiveRepository;

    @Transactional
    public Share share(Share share) {
        share.setToken(generateToken());
        Share savedShare = shareRepositroy.save(share);
        long[] dividedMoney = divide(savedShare);

        int i=0;
        for (long money : dividedMoney) {
            Receive receive = new Receive();
            receive.setToken(savedShare.getToken());
            receive.setSequence(i++);
            receive.setMoney(money);
            receive.setRoomId(savedShare.getRoomId());
            receiveRepository.save(receive);
        }
        return savedShare;
    }

    @Transactional
    public Optional<Share> search(String token) {
        return shareRepositroy.findById(token);
    }

    @Transactional
    public void receive(Receive receive) {
        receiveRepository.save(receive);
        Optional<Share> shareOptional = shareRepositroy.findById(receive.getToken());
        shareOptional.ifPresent(share ->  {
            share.getReceiveList().add(receive);
            shareRepositroy.save(share);
        });
    }

    @Transactional
    public List<Receive> findReceiveList(String token, String roomId) {
        List<Optional<Receive>> receiveOptionalList = receiveRepository.
                findAllByTokenAndRoomIdOrderBySequenceAsc(token, roomId);

        return receiveOptionalList.stream()
                .filter(receiveOpt -> receiveOpt.isPresent())
                .map(receiveOpt -> receiveOpt.get()).collect(Collectors.toList());

    }

    @Transactional
    public boolean isSelfReceive(String token, Long userId) {
        return shareRepositroy.findByTokenAndUserId(token, userId).isPresent();
    }

    @Transactional
    public boolean isTimeOverToken(String token) {
        return shareRepositroy.findById(token)
                .stream()
                .map(share->share.getCreatedAt())
                .anyMatch(created -> LocalDateTime.now().isAfter(created.plusMinutes(10)));
    }


    public boolean isDuplicatedUserReceive(List<Receive> receiveList, Long userId) {
        return receiveList.stream()
                .filter(receive -> receive.isReceived())
                .map(receive -> receive.getUserId())
                .anyMatch(user -> userId.equals(user));
    }


    String generateToken() {
        return TokenType.ALPHA.getTokenGenerator().generateToken(3);
    }

    long[] divide(Share share) {
        return share.getShareType().getDistributer().distribute(share.getMoney(), share.getCount());
    }


}
