package zerobase.commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PwChangeDto {

    private String oldPassword;
    private String newPassword;
}
