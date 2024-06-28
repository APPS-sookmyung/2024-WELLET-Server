package WELLET.welletServer.card.dto;

import WELLET.welletServer.card.domain.Card;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class CardUpdateDto {

    private String name;

    private String position;


    private String email;
    private String phone;
    private String tel;
    private String department;

    private String company;
    private String address;
    private String memo;

    private String qr;
    private String profile_image;

    public static CardUpdateDto toCardUpdateDto(Card card) {
        return CardUpdateDto.builder()
                .name(card.getName())
                .position(card.getPosition())
                .email(card.getEmail())
                .phone(card.getPhone())
                .tel(card.getTel())
                .department(card.getDepartment())
                .company(card.getCompany())
                .address(card.getAddress())
                .memo(card.getMemo())
                .qr(card.getQr())
                .profile_image(card.getProfile_image())
                .build();

    }
}
