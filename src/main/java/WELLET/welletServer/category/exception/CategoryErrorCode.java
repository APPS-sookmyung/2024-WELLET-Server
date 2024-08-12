package WELLET.welletServer.category.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryErrorCode {
    CATEGORY_NOT_FOUND("그룹을 찾을 수 없습니다."),
    CATEGORY_DUPLICATE("이미 존재하는 그룹입니다.");

    private final String message;
}
