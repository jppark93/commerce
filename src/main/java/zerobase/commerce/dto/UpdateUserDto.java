package zerobase.commerce.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.commerce.domain.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserDto {

    private String email;
    private String name;
    private String phone;

    public static UpdateUserDto from(User user) {
        return UpdateUserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhoneNumber())
                .build();
    }

}
