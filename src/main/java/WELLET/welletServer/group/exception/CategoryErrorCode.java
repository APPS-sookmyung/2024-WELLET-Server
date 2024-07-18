package WELLET.welletServer.group.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryErrorCode {
    CATEGORY_ERROR_CODE("그룹을 찾을 수 없습니다.");

    private final String message;
}
