package WELLET.welletServer.card.service;

import WELLET.welletServer.card.Repository.CardRepository;
import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.dto.*;
import WELLET.welletServer.card.exception.CardErrorCode;
import WELLET.welletServer.card.exception.CardException;
import WELLET.welletServer.categoryCard.domain.CategoryCard;
import WELLET.welletServer.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {
    private final CardRepository cardRepository;

    @Transactional
    public Card saveCard (Member member, CardSaveDto dto) {
        Card card = Card.builder()
                .name(dto.getName())
                .position(dto.getPosition())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .tel(dto.getTel())
                .department(dto.getDepartment())
                .company(dto.getCompany())
                .address(dto.getAddress())
                .address(dto.getMemo())
                .member(member)
                .build();

        cardRepository.save(card);


        return card;
//        return CardResponse.toCardDto(card);
    }

    public CardResponse addCategory(Card card, List<CategoryCard> categoryCards, List<String> categories) {
//        Card card = Card.builder()
//                .categoryCardList(categoryCards)
//                .build();

        card.addCardCategory(categoryCards);
        Card cardResponse = cardRepository.save(card);
        return CardResponse.toCardDto(cardResponse, categories);
    }

    public CardCountResponseDto findAllCard() {
        List<Card> cardList = cardRepository.findAll();
        // Entity -> DTO
        List<CardListResponse> cards = cardList.stream()
                .map(CardListResponse::toCardList)
                .toList();

        return new CardCountResponseDto(cardRepository.count(), cards);
    }

    public CardResponse findCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardException(CardErrorCode.CARD_NOT_FOUND));

        return CardResponse.toCardDto(card, findCategoryCardName(card));
    }

    private List<String> findCategoryCardName(Card card) {
        List<String> categoryCards = new ArrayList<>();

        for (CategoryCard categoryCard : card.getCategoryCards()) {
            categoryCards.add(categoryCard.getCategory().getName());
        }

        return categoryCards;
    }



    @Transactional
    public CardUpdateDto updateCard(Long cardId, CardUpdateDto dto) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardException(CardErrorCode.CARD_NOT_FOUND));

        card.updateCard(dto);
        return CardUpdateDto.toCardUpdateDto(card);
    }

    @Transactional
    public long deleteCard(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardException(CardErrorCode.CARD_NOT_FOUND));
        cardRepository.delete(card);
        return cardId;
    }

    @Transactional
    public void deleteCardList(List<Long> cardIdList) {
        cardRepository.deleteAllByIdInBatch(cardIdList);
    }
}
