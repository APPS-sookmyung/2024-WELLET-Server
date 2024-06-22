package WELLET.welletServer.card.dto;

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
}
