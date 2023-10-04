package zerobase.commerce.dto;

import lombok.*;
import zerobase.commerce.domain.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDto {

    private String userName;
    private String email;
    private String name;
    private String address;
    private String password;
    private String phone;

    public SignUpDto(User user){
        userName = user.getUserName();
        email = user.getEmail();
        name = user.getName();
        address = user.getAddress();
        password = user.getPassword();
        phone = user.getPhoneNumber();

    }
}
