package WELLET.welletServer.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode {
    MEMBER_NOT_FOUND("회원을 찾을 수 없습니다.");

    private final String message;
}
