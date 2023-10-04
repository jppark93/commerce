package zerobase.commerce.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInDto {
    private String userName;
    private String password;
}
