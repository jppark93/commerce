package zerobase.commerce.domain;


import lombok.*;
import zerobase.commerce.dto.SignInDto;
import zerobase.commerce.dto.SignUpDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,name = "user_name", nullable = false)
    private String userName;
    private String password;

    @Column(unique = true)
    private String email;
    private String address;
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    public static User from(SignUpDto signUpDto) {
        return User.builder()
                .userName(signUpDto.getUserName())
                .email(signUpDto.getEmail())
                .name(signUpDto.getName())
                .address(signUpDto.getAddress())
                .password(signUpDto.getPassword())
                .phoneNumber(signUpDto.getPhone())
                .build();
    }


}
