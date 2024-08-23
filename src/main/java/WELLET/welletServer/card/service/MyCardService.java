package WELLET.welletServer.card.service;

import WELLET.welletServer.card.Repository.CardRepository;
import WELLET.welletServer.card.domain.Card;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyCardService {

    private final CardRepository cardRepository;
    private final S3FileUploader s3FileUploader;

    @Transactional
    public Card saveCard (Long memberId, MyCardSaveDto dto) {


        cardRepository.findByOwnerId(memberId).ifPresent(e -> {
            throw new CardException(CardErrorCode.DUPLICATE_MY_CARD);
        });

        Card card = Card.builder()
                .name(dto.getName())
                .company(dto.getCompany())
                .role(dto.getRole())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .tel(dto.getTel())
                .address(dto.getAddress())
//                .profImgUrl(dto.getProfImgUrl())
//                .frontImgUrl(dto.getFrontImgUrl())
//                .backImgUrl(dto.getBackImgUrl())
                .ownerId(memberId)
                .build();

        return cardRepository.save(card);
    }

    public void saveprocessImages(Long memberId, MyCardSaveDto dto) {
        List<MultipartFile> images = Arrays.asList(dto.getProfImg(), dto.getFrontImg(), dto.getBackImg());
        List<String> paths = Arrays.asList("user-profiles/", "user-front/", "user-back/");
        List<Consumer<String>> urlSetters = Arrays.asList(dto::setProfImgUrl, dto::setFrontImgUrl, dto::setBackImgUrl);

        for (int i = 0; i < images.size(); i++) {
            String path = paths.get(i) + memberId + "/";
            uploadAndSetImage(images.get(i), path, urlSetters.get(i));
        }
    }

    public void updateprocessImages(Long memberId, MyCardUpdateDto dto) {
        List<MultipartFile> images = Arrays.asList(dto.getProfImg(), dto.getFrontImg(), dto.getBackImg());
        List<String> paths = Arrays.asList("user-profiles/", "user-front/", "user-back/");
        List<Consumer<String>> urlSetters = Arrays.asList(dto::setProfImgUrl, dto::setFrontImgUrl, dto::setBackImgUrl);

        for (int i = 0; i < images.size(); i++) {
            String path = paths.get(i) + memberId + "/";
            uploadAndSetImage(images.get(i), path, urlSetters.get(i));
        }
    }

    private void uploadAndSetImage(MultipartFile image, String path, Consumer<String> urlSetter) {
        if (image != null && !image.isEmpty()) {
            String imageUrl = s3FileUploader.uploadFile(image, path);
            urlSetter.accept(imageUrl);
    }}


    public MyCardResponse findMyCard(Long memberId) {
        Card card = cardRepository.findByOwnerId(memberId)
                .orElse(null);

        if (card == null) {
            return null;
        }

        return MyCardResponse.toCardDto(card);
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
