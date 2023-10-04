package zerobase.commerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.commerce.type.ErrorCode;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserException extends RuntimeException {


    private ErrorCode errorCode;
    private String message;

    public UserException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getDescription();
    }
}
