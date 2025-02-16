package WELLET.welletServer.card.service;

import WELLET.welletServer.card.Repository.CardRepository;
import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.dto.*;
import WELLET.welletServer.card.exception.CardErrorCode;
import WELLET.welletServer.card.exception.CardException;
import WELLET.welletServer.files.S3FileUploader;
import WELLET.welletServer.member.domain.Member;
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
    private final S3FileUploader s3FileUploader;

    @Transactional
    public Card saveCard (Member member, MyCardSaveDto dto) {
        String profileImageUrl = null;


        cardRepository.findByOwnerId(member.getId()).ifPresent(e -> {
            throw new CardException(CardErrorCode.DUPLICATE_MY_CARD);
        });
        if (dto.getProfImg() != null && !dto.getProfImg().isEmpty()) {
            profileImageUrl = s3FileUploader.uploadFile(dto.getProfImg(), "profile_image");
        }
        Card card = Card.builder()
                .name(dto.getName())
                .company(dto.getCompany())
                .position(dto.getPosition())
                .department(dto.getDepartment())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .tel(dto.getTel())
                .address(dto.getAddress())
                .profImgUrl(profileImageUrl)
                .member(member)
                .ownerId(member.getId())
                .build();

        return cardRepository.save(card);
    }

    public MyCardResponse findMyCard(Member member) {
        Card card = cardRepository.findByOwnerId(member.getId())
                .orElse(null);

        if (card == null) {
            return null;
        }

        return MyCardResponse.toCardDto(card);
    }

    @Transactional
    public MyCardResponseContent updateMyCardContent(Member member, MyCardUpdateDtoContent dto) {
        Card card = findCard(member);
        card.updateCard(dto);
        return MyCardResponseContent.toCardDto(card);
    }

    @Transactional
    public MyCardResponseProfImg updateMyCardProfImg(Member member, MyCardUpdateDtoProfImg dto) {
        String newProfImgUrl = null;

        Card card = findCard(member);
        if (card.getProfImgUrl() != null) {
            s3FileUploader.deleteFile(card.getProfImgUrl(), "profile_image");
        }

        if (dto.getProfImg() != null && !dto.getProfImg().isEmpty()) {
            newProfImgUrl = s3FileUploader.uploadFile(dto.getProfImg(), "profile_image");
        }
        card.updateCard(newProfImgUrl);

        return MyCardResponseProfImg.toCardDto(card);
    }

    @Transactional
    public void deleteMyCard(Member member) {
        Card card = findCard(member);
        cardRepository.delete(card);
        if (card.getProfImgUrl() != null) {
            s3FileUploader.deleteFile(card.getProfImgUrl(), "profile_image");
        }
    }

    private Card findCard(Member member) {
        return cardRepository.findByOwnerId(member.getId())
                .orElseThrow(() -> new CardException(CardErrorCode.CARD_NOT_FOUND));
    }
}
