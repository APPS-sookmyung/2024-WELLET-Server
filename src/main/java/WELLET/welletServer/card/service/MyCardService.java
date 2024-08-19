package WELLET.welletServer.card.service;

import WELLET.welletServer.card.Repository.CardRepository;
import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.dto.CardResponse;
import WELLET.welletServer.card.dto.MyCardUpdateDto;
import WELLET.welletServer.card.exception.CardErrorCode;
import WELLET.welletServer.card.exception.CardException;
import WELLET.welletServer.card.dto.MyCardSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyCardService {

    private final CardRepository cardRepository;

    @Transactional
    public Card saveCard (Long memberId, MyCardSaveDto dto) {


        cardRepository.findByOwnerId(memberId).ifPresent(e -> {
            throw new CardException(CardErrorCode.DUPLICATE_MY_CARD);
        });

        Card card = Card.builder()
                .name(dto.getName())
                .position(dto.getPosition())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .tel(dto.getTel())
                .department(dto.getDepartment())
                .company(dto.getCompany())
                .address(dto.getAddress())
                .ownerId(memberId)
                .build();

        return cardRepository.save(card);
    }

    public CardResponse findMyCard(Long memberId) {
        Card card = cardRepository.findByOwnerId(memberId)
                .orElse(null);

        if (card == null) {
            return null;
        }

        return CardResponse.toCardDto(card);
    }

    @Transactional
    public MyCardUpdateDto updateMyCard(Long memberId, MyCardUpdateDto dto) {
        Card card = cardRepository.findByOwnerId(memberId)
                .orElseThrow(() -> new CardException(CardErrorCode.CARD_NOT_FOUND));
        card.updateCard(dto);
        return MyCardUpdateDto.toCardUpdateDto(card);
    }

    @Transactional
    public void deleteMyCard(Long memberId) {
        Card card = cardRepository.findByOwnerId(memberId)
                .orElseThrow(() -> new CardException(CardErrorCode.CARD_NOT_FOUND));
        cardRepository.delete(card);
    }
}