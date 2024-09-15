package WELLET.welletServer.card.service;

import WELLET.welletServer.card.Repository.CardRepository;
import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.dto.*;
import WELLET.welletServer.card.exception.CardErrorCode;
import WELLET.welletServer.card.exception.CardException;
import WELLET.welletServer.category.domain.Category;
import WELLET.welletServer.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {
    private final CardRepository cardRepository;

    @Transactional
    public Card saveCard (Member member, Category category, CardSaveDto dto) {
        Card card = Card.builder()
                .name(dto.getName())
                .company(dto.getCompany())
                .role(dto.getRole())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .tel(dto.getTel())
                .address(dto.getAddress())
                .memo(dto.getMemo())
                .member(member)
                .category(category)
                .build();

        return cardRepository.save(card);
    }

    public CardCountResponseDto findAllCard(Member member) {
        List<Card> cardList = cardRepository.findByMember(member);
        // Entity -> DTO
        List<CardListResponse> cards = cardList.stream()
                .map(CardListResponse::toCardList)
                .toList();

        return new CardCountResponseDto(cardList.size(), cards);
    }

    public CardResponse findCard(Long cardId) {
        Card card = findOne(cardId);
        String categoryName = "";
        if (card.getCategory() != null) {
            categoryName = card.getCategory().getName();
        }
        return CardResponse.toCardDto(card, categoryName);
    }

    @Transactional
    public CardUpdateDto updateCard(Long cardId, CardUpdateDto dto) {
        Card card = findOne(cardId);

        card.updateCard(dto);
        return CardUpdateDto.toCardUpdateDto(card);
    }

    @Transactional
    public void deleteCard(Long cardId) {
        Card card = findOne(cardId);
        cardRepository.delete(card);
    }

    @Transactional
    public void deleteCardList(List<Long> cardIds) {
        for (Long cardId : cardIds) {
            deleteCard(cardId);
        }
    }

    public Card findOne(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new CardException(CardErrorCode.CARD_NOT_FOUND));
    }
  
    public CardCountResponseDto searchCards(String keyword) {
        List<Card> cardList = cardRepository.searchCards(keyword);
        List<CardListResponse> cards = cardList.stream()
                .map(CardListResponse::toCardList)
                .toList();
        return new CardCountResponseDto(cards.size(), cards);
    }

    public CardCountResponseDto findByCategory(Member member, Category category) {
        List<Card> cardList = findCategoryReturnCard(member, category);
        List<CardListResponse> cards = cardList.stream()
                .map(CardListResponse::toCardList)
                .toList();

        return new CardCountResponseDto(cardList.size(), cards);
    }


    public List<Card> findCategoryReturnCard (Member member, Category category) {
        return cardRepository.findByCategoryAndMember(category, member);
    }
}
