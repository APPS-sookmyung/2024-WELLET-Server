package WELLET.welletServer.card.service;

import WELLET.welletServer.card.Repository.CardImageRepository;
import WELLET.welletServer.card.Repository.CardRepository;
import WELLET.welletServer.card.domain.Card;
import WELLET.welletServer.card.domain.CardImage;
import WELLET.welletServer.card.dto.MyCardResponse;
import WELLET.welletServer.card.dto.MyCardSaveDto;
import WELLET.welletServer.card.dto.MyCardUpdateDto;
import WELLET.welletServer.card.exception.CardErrorCode;
import WELLET.welletServer.card.exception.CardException;
import WELLET.welletServer.files.S3FileUploader;
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
    private final CardImageRepository cardImageRepository;

    @Transactional
    public Card saveCard (Long memberId, MyCardSaveDto dto) {
        String profileImageUrl = null;

        cardRepository.findByOwnerId(memberId).ifPresent(e -> {
            throw new CardException(CardErrorCode.DUPLICATE_MY_CARD);
        });
        if (dto.getProfImg() != null) {
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
//                .profImgUrl(dto.getProfImgUrl())
//                .frontImgUrl(dto.getFrontImgUrl())
//                .backImgUrl(dto.getBackImgUrl())
                .profImgUrl(profileImageUrl)
                .ownerId(memberId)
                .build();

        return cardRepository.save(card);
    }

    public MyCardResponse findMyCard(Long memberId) {
        Card card = cardRepository.findByOwnerId(memberId)
                .orElse(null);

        if (card == null) {
            return null;
        }

        return MyCardResponse.toCardDto(card);
    }

    @Transactional
    public MyCardResponse updateMyCard(Long memberId, MyCardUpdateDto dto) {
        String newProfImgUrl = null;

        Card card = cardRepository.findByOwnerId(memberId)
                .orElseThrow(() -> new CardException(CardErrorCode.CARD_NOT_FOUND));

        if (dto.getProfile_Img() != null) {
            if (card.getProfImgUrl() != null) {
                s3FileUploader.deleteFile(card.getProfImgUrl(), "profile_image");
            }
            newProfImgUrl = s3FileUploader.uploadFile(dto.getProfile_Img(), "profile_image");
        }
        card.updateCard(dto, newProfImgUrl);
        return MyCardResponse.toCardDto(card);
    }

    @Transactional
    public void deleteMyCard(Long memberId) {
        Card card = cardRepository.findByOwnerId(memberId)
                .orElseThrow(() -> new CardException(CardErrorCode.CARD_NOT_FOUND));
        cardRepository.delete(card);
        s3FileUploader.deleteFile(card.getProfImgUrl(), "profile_image");
    }
}
