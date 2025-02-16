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
import WELLET.welletServer.member.exception.MemberErrorCode;
import WELLET.welletServer.member.exception.MemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
                .position(dto.getPosition())
                .department(dto.getDepartment())
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

    @Transactional
    public CardImage saveCardImage (Card card, CardSaveDto dto) {
            String frontImgUrl = dto.getFrontImg() != null && !dto.getFrontImg().isEmpty() ? s3FileUploader.uploadFile(dto.getFrontImg(), "front_image") : null;
            String backImgUrl = dto.getBackImg() != null && !dto.getBackImg().isEmpty() ? s3FileUploader.uploadFile(dto.getBackImg(), "back_image") : null;
            String profImgUrl = dto.getProfImg() != null && !dto.getProfImg().isEmpty() ? s3FileUploader.uploadFile(dto.getProfImg(), "prof_image") : null;

            CardImage cardImage = CardImage.builder()
                    .front_img_url(frontImgUrl)
                    .back_img_url(backImgUrl)
                    .prof_img_url(profImgUrl)
                    .card(card)
                    .build();
            return cardImageRepository.save(cardImage);
    }

    public CardCountResponseDto findAllCard(Member member) {
        List<Card> cardList = cardRepository.findByMember(member)
                .stream()
                .filter(card -> card.getOwnerId() == null)
                .toList();

        // Entity -> DTO
        List<CardListResponse> cards = cardList.stream()
                .map(CardListResponse::toCardList)
                .toList();

        return new CardCountResponseDto(cardList.size(), cards);
    }

    public CardResponse findCard(Member member, Long cardId) {
        Card card = findOne(member, cardId);
        CardImage cardImage = cardImageRepository.findByCard(card);
        String categoryName = "";
        if (card.getCategory() != null) {
            categoryName = card.getCategory().getName();
        }
        return CardResponse.toCardDto(card, categoryName, cardImage);
    }

    @Transactional
    public Card updateCard(Member member, Long cardId, CardUpdateDto dto) {
        Card card = findOne(member, cardId);
        card.updateCard(dto);
        return card;
    }

    @Transactional
    public CardImage updateCardImage(Card card, CardUpdateDto dto) {
        CardImage cardImage = cardImageRepository.findByCard(card);
        String newFrontImgUrl, newBackImgUrl, newProfImgUrl;

        deleteCardImage(cardImage);

        // 명함 이미지 (앞)
        if (dto.getFrontImg() != null && !dto.getFrontImg().isEmpty()) {
            newFrontImgUrl = s3FileUploader.uploadFile(dto.getFrontImg(), "front_image");
            cardImage.updateFrontImage(newFrontImgUrl);
        }

        // 명함 이미지 (뒤)
        if (dto.getBackImg()!= null && !dto.getBackImg().isEmpty()) {
            newBackImgUrl = s3FileUploader.uploadFile(dto.getBackImg(), "back_image");
            cardImage.updateBackImage(newBackImgUrl);
        }

        // 명함 이미지 (프로필)
        if (dto.getProfImg() != null && !dto.getProfImg().isEmpty()) {
            newProfImgUrl = s3FileUploader.uploadFile(dto.getFrontImg(), "prof_image");
            cardImage.updateProfImage(newProfImgUrl);
        }

        return cardImage;
    }

    @Transactional
    public void deleteCard(Member member, Long cardId) {
        Card card = findOne(member, cardId);
        CardImage cardImage = cardImageRepository.findByCard(card);
        deleteCardImage(cardImage);
        cardImageRepository.delete(cardImage);
        cardRepository.delete(card);

    }

    @Transactional
    public void deleteCardList(Member member, List<Long> cardIds) {
        for (Long cardId : cardIds) {
            deleteCard(member, cardId);
        }
    }

    public Card findOne(Member member, Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardException(CardErrorCode.CARD_NOT_FOUND));
        if (card.getMember() != member) throw new MemberException(MemberErrorCode.UNAUTHORIZED_USER);
        return card;
    }

    public CardCountResponseDto searchCards(String keyword) {

        List<Card> cardList = cardRepository.searchCards(keyword)
                .stream()
                .filter(card -> card.getOwnerId() == null)
                .toList();

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

    private void deleteCardImage(CardImage cardImage) {
        if (cardImage.getFront_img_url() != null) {
            s3FileUploader.deleteFile(cardImage.getFront_img_url(), "front_image");
            cardImage.updateFrontImage(null);
        }

        if (cardImage.getBack_img_url() != null) {
            s3FileUploader.deleteFile(cardImage.getBack_img_url(), "back_image");
            cardImage.updateBackImage(null);
        }

        if (cardImage.getProf_img_url() != null) {
            s3FileUploader.deleteFile(cardImage.getProf_img_url(), "prof_image");
            cardImage.updateProfImage(null);
        }
    }
}

