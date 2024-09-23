package WELLET.welletServer.card.service;

import WELLET.welletServer.card.Repository.CardImageRepository;
import WELLET.welletServer.card.Repository.CardRepository;
import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.domain.CardImage;
import WELLET.welletServer.card.dto.*;
import WELLET.welletServer.card.exception.CardErrorCode;
import WELLET.welletServer.card.exception.CardException;
import WELLET.welletServer.category.domain.Category;
import WELLET.welletServer.files.S3FileUploader;
import WELLET.welletServer.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {
    private final CardRepository cardRepository;
    private final S3FileUploader s3FileUploader;
    private final CardImageRepository cardImageRepository;

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
//                .profImgUrl(dto.getProfImgUrl())
//                .frontImgUrl(dto.getFrontImgUrl())
//                .backImgUrl(dto.getBackImgUrl())
                .build();

        return cardRepository.save(card);
    }

    @Transactional
    public CardImage saveCardImage (Card card, CardSaveDto dto) {

        if (dto.getFrontImg() != null || dto.getBackImg() != null) {

            String frontImgUrl = dto.getFrontImg() != null ? s3FileUploader.uploadFile(dto.getFrontImg(), "front-image") : null;
            String backImgUrl = dto.getBackImg() != null ? s3FileUploader.uploadFile(dto.getBackImg(), "back-image") : null;

            CardImage cardImage = CardImage.builder()
                    .front_img_url(frontImgUrl)
                    .back_img_url(backImgUrl)
                    .card(card)
                    .build();
            return cardImageRepository.save(cardImage);
        }
        return null;
    }

//    @Transactional
//    public CardResponse addCategory(Card card, L categoryCards, List<String> categories, CardImage cardImage) {
//        card.addCardCategory(categoryCards);
//        card = cardRepository.save(card);
//        return CardResponse.toCardDto(card, categories, cardImage);
//    }
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
    public Card updateCard(Long cardId, CardUpdateDto dto) {
        Card card = findOne(cardId);
        String newProfImgUrl = null;

        if (dto.getProfImg() != null) {
            if (card.getProfImgUrl() != null) {
                s3FileUploader.deleteFile(card.getProfImgUrl(), "profile_image");
            }
            newProfImgUrl = s3FileUploader.uploadFile(dto.getProfImg(), "profile_image");
        }

        card.updateCard(dto, newProfImgUrl);

        return card;
    }
    

     @Transactional
        public void deleteCard(Long cardId) {
            Card card = findOne(cardId);
            cardRepository.delete(card);
        CardImage cardImage = cardImageRepository.findByCard(card);

        String newFrontImgUrl = null;
        String newBackImgUrl = null;

        if (dto.getFrontImg() != null) {
            if (cardImage.getFront_img_url() != null) {
                s3FileUploader.deleteFile(cardImage.getFront_img_url(), "front-image");
            }
            newFrontImgUrl = s3FileUploader.uploadFile(dto.getFrontImg(), "front-image");
        }
        if (dto.getBackImg() != null) {
            if (cardImage.getBack_img_url() != null) {
                s3FileUploader.deleteFile(cardImage.getBack_img_url(), "back-image");
            }
            newBackImgUrl = s3FileUploader.uploadFile(dto.getBackImg(), "back-image");
        }
        if (cardImage != null) {
            cardImage.updateCardImage(newFrontImgUrl, newBackImgUrl);
        }
        return cardImage;
    }


    @Transactional
    public void deleteCard(Long card_id) {
        Card card = cardRepository.findById(card_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 명함이 없습니다. id: " + card_id));
        cardRepository.delete(card);
        CardImage cardImage = cardImageRepository.findByCard(card);
        if (card.getProfImgUrl() != null | cardImage.getFront_img_url() != null | cardImage.getBack_img_url() != null) {
            if (card.getProfImgUrl() != null) {
                s3FileUploader.deleteFile(card.getProfImgUrl(), "profile_image");
            }
            if (cardImage.getFront_img_url() != null) {
                s3FileUploader.deleteFile(cardImage.getFront_img_url(), "front-image");
            }
            if (cardImage.getBack_img_url() != null) {
                s3FileUploader.deleteFile(cardImage.getBack_img_url(), "front-image");
            }
        }
        cardImageRepository.delete(cardImage);
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

