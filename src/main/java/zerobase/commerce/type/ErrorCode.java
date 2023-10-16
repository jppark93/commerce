package zerobase.commerce.type;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {

    EMAIL_ALREADY_EXIST("해당 이메일은 이미 사용중입니다."),
    WRONG_PASSWORD("비밀번호가 일치하지 않습니다."),

    WRONG_OLD_PASSWORD("현재 비밀번호가 입력한 비밀번호와 일치하지 않습니다."),
    PASSWORD_NO_CHANGE("현재 비밀번호와 새 비밀번호가 일치합니다."),
    USER_ALEADY_EXIST("해당 계정은 이미 사용중입니다."),
    USER_NOT_EXIST("해당 계정은 존재하지 않습니다."),
    ORDER_NOT_EXIST("주문을 조회할 수 없습니다."),
    PRODUCT_NOT_EXIST("해당 상품은 존재하지 않습니다."),
    CART_NOT_EXIST("카트에 상품이 존재하지 않습니다.")
    ;

    private String description;
}
