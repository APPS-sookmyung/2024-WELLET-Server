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
import java.util.stream.Collectors;

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

        return cardRepository.save(card);
    }

    @Transactional
    public CardResponse addCategory(Card card, List<CategoryCard> categoryCards, List<String> categories) {
        card.addCardCategory(categoryCards);
        card = cardRepository.save(card);
        return CardResponse.toCardDto(card, categories);
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
        Card card = findOne(cardId);
        return CardResponse.toCardDto(card, findCategoryCardNames(card));
    }

    private List<String> findCategoryCardNames(Card card) {
        return card.getCategoryCards().stream()
                .map(categoryCard -> categoryCard.getCategory().getName())
                .collect(Collectors.toList());
    }

    @Transactional
    public CardUpdateDto updateCard(Long cardId, CardUpdateDto dto) {
        Card card = findOne(cardId);

        card.updateCard(dto);
        return CardUpdateDto.toCardUpdateDto(card);
    }

    @Transactional
    public void deleteCard(Card card) {
        cardRepository.delete(card);
    }

    public Card findOne(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new CardException(CardErrorCode.CARD_NOT_FOUND));
    }

    @Transactional
    public void deleteCardList(List<Long> cardIdList) {
        cardRepository.deleteAllByIdInBatch(cardIdList);
    }

    public CardCountResponseDto searchCards(String keyword) {
        List<Card> cardList = cardRepository.searchCards(keyword);
        // Entity -> DTO
        List<CardListResponse> cards = cardList.stream()
                .map(CardListResponse::toCardList)
                .toList();
        return new CardCountResponseDto((long) cards.size(), cards);
    }
}
