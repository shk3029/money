package com.kakaopay.money.share.service;

import com.kakaopay.money.advice.exception.ReceiveNotFoundException;
import com.kakaopay.money.advice.exception.RecevieAccessDeniedException;
import com.kakaopay.money.advice.exception.TimeOverException;
import com.kakaopay.money.advice.exception.TokenNotFoundException;
import com.kakaopay.money.constant.TokenType;
import com.kakaopay.money.share.dto.ReceiveDto;
import com.kakaopay.money.share.dto.SearchDto;
import com.kakaopay.money.share.entity.Receive;
import com.kakaopay.money.share.entity.Share;
import com.kakaopay.money.share.repository.ReceiveRepository;
import com.kakaopay.money.share.repository.ShareRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    public ReceiveDto receive(String token, String roomId, Long userId) {
        List<Optional<Receive>> receiveOptionalList = receiveRepository.
                findAllByTokenAndRoomIdOrderBySequenceAsc(token, roomId);

        List<Receive> receiveList = receiveOptionalList.stream()
                .filter(receiveOpt -> receiveOpt.isPresent())
                .map(receiveOpt -> receiveOpt.get()).collect(Collectors.toList());

        if (receiveList.isEmpty()) throw new TokenNotFoundException("토큰이 존재하지 않거나 현재 대화방이 다릅니다");
        if (isSelfReceive(token, userId)) throw new RecevieAccessDeniedException("본인이 뿌린 돈을 받을 수 없습니다");
        if (isTimeOverToken(token)) throw new TimeOverException("10분");
        if (isDuplicatedUserReceive(receiveList, userId)) throw new RecevieAccessDeniedException("중복해서 받을 수 없습니다");

        Optional<Receive> receiveOptional = receiveList.stream()
                .filter(receive -> !receive.isReceived())
                .findFirst();

        receiveOptional.ifPresent(receive -> {
            receive.setToken(token);
            receive.setUserId(userId);
            receive.setReceived(true);
            receiveRepository.save(receive);
            Optional<Share> shareOptional = shareRepositroy.findById(receive.getToken());
            shareOptional.ifPresent(share ->  {
                share.getReceiveList().add(receive);
                shareRepositroy.save(share);
            });
        });

        ReceiveDto receiveDto = receiveToReceiveDto(receiveOptional.orElseThrow(()->new ReceiveNotFoundException()));
        return receiveDto;
    }

    public SearchDto search(String token, Long userId) {
        Share share = shareRepositroy.findById(token).orElse(new Share());
        if (StringUtils.isEmpty(share.getToken())) throw new TokenNotFoundException();
        if (share.getUserId().longValue() != userId) throw new RecevieAccessDeniedException();
        if (LocalDateTime.now().isAfter(share.getCreatedAt().plusDays(7))) throw new TimeOverException("7일");
        return shareToSearchDto(share);
    }

    String generateToken() {
        return TokenType.ALPHA.getTokenGenerator().generateToken(3);
    }

    long[] divide(Share share) {
        return share.getShareType().getDistributer().distribute(share.getMoney(), share.getCount());
    }


    boolean isSelfReceive(String token, Long userId) {
        return shareRepositroy.findByTokenAndUserId(token, userId).isPresent();
    }

    boolean isTimeOverToken(String token) {
        return shareRepositroy.findById(token)
                .stream()
                .map(share->share.getCreatedAt())
                .anyMatch(created -> LocalDateTime.now().isAfter(created.plusMinutes(10)));
    }

    boolean isDuplicatedUserReceive(List<Receive> receiveList, Long userId) {
        return receiveList.stream()
                .filter(receive -> receive.isReceived())
                .map(receive -> receive.getUserId())
                .anyMatch(user -> userId.equals(user));
    }

    private SearchDto shareToSearchDto(Share share) {
        SearchDto searchDto = new SearchDto();
        searchDto.setMoney(share.getMoney());
        searchDto.setCreatedAt(share.getCreatedAt());

        share.getReceiveList().stream()
                .filter(receive -> receive.isReceived())
                .forEachOrdered(receive -> searchDto.setSearchDto(receive));

        searchDto.sumReceivedMoney();
        return searchDto;
    }

    private ReceiveDto receiveToReceiveDto(Receive receive) {
        ReceiveDto receiveDto = new ReceiveDto();
        receiveDto.setMoney(receive.getMoney());
        receiveDto.setUserId(receive.getUserId());
        return receiveDto;
    }
}
