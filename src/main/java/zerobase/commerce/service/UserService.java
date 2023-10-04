package zerobase.commerce.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.commerce.dto.SignInDto;
import zerobase.commerce.exception.UserException;
import zerobase.commerce.domain.User;
import zerobase.commerce.dto.SignUpDto;
import zerobase.commerce.repository.UserRepository;

import static zerobase.commerce.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public String signUp(SignUpDto signUpDto) throws UserException {

        if(userRepository.existsByUserName(signUpDto.getUserName())){
            throw new UserException(USER_ALEADY_EXIST);
        }
        User user = User.from(signUpDto);

        userRepository.save(user);
        return "회원 가입이 완료 되었습니다.";
    }

    public String signIn(SignInDto signInDto) throws UserException{
       User user = userRepository.findByUserName(signInDto.getUserName())
               .orElseThrow(()-> new UserException(USER_NOT_EXIST));

       if(!signInDto.getPassword().equals(user.getPassword())){
           throw new UserException(WRONG_PASSWORD);
       }

       return "로그인 완료";
    }

}

