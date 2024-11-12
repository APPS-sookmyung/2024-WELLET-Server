package WELLET.welletServer.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode {
    MEMBER_NOT_FOUND("회원을 찾을 수 없습니다."),
    DUPLICATE_USERNAME("중복된 회원입니다."),
    UNAUTHORIZED_USER("인증된 사용자가 아닙니다.");
    private final String message;
}
