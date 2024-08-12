package WELLET.welletServer.member.exception;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException{
    private MemberErrorCode code;
    private String message;

    public MemberException(MemberErrorCode code) {
        super();
        this.code = code;
        this.message = code.getMessage();
    }

}
