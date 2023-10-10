package zerobase.commerce.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.commerce.dto.PwChangeDto;
import zerobase.commerce.dto.SignInDto;
import zerobase.commerce.dto.UpdateUserDto;
import zerobase.commerce.exception.UserException;
import zerobase.commerce.domain.User;
import zerobase.commerce.dto.SignUpDto;
import zerobase.commerce.repository.UserRepository;
import zerobase.commerce.security.Crypto;
import zerobase.commerce.security.JWT;

import static zerobase.commerce.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final JWT authService;

    public String signUp(SignUpDto signUpDto) throws UserException {

        if(userRepository.existsByUserName(signUpDto.getUserName())){
            throw new UserException(USER_ALEADY_EXIST);
        }
        User user = User.from(signUpDto);

        user.setPassword(Crypto.encrypt(user.getPassword()));

        userRepository.save(user);

        return "회원 가입이 완료 되었습니다.";
    }

    public String signIn(SignInDto signInDto) throws UserException{
       User user = userRepository.findByUserName(signInDto.getUserName())
               .orElseThrow(()-> new UserException(USER_NOT_EXIST));

       if(!signInDto.getPassword().equals(user.getPassword())){
           throw new UserException(WRONG_PASSWORD);

       }

       //return "로그인 완료";
        return authService.createToken(user.getId(), user.getUserName());
    }
    public void changePassword(Long id, PwChangeDto pwChangeDto) {
        // 해당하는 유저가 존재하지 않을경우
        User users = userRepository.findById(id)
                .orElseThrow(()-> new UserException(USER_NOT_EXIST));

        String decryptedPassword = Crypto.decrypt(users.getPassword());

        // 입력받은 지금 비밀번호가 실제와 일치하지 않는 경우
        if (!decryptedPassword.equals(pwChangeDto.getOldPassword())) {
            throw new UserException(WRONG_OLD_PASSWORD);
        }

        // 지금 비밀번호와 새 비밀번호가 같은 경우
        if (pwChangeDto.getOldPassword()
                .equals(pwChangeDto.getNewPassword())) {
            throw new UserException(PASSWORD_NO_CHANGE);
        }

        users.setPassword(Crypto.encrypt(pwChangeDto.getNewPassword()));
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(USER_NOT_EXIST));

        userRepository.delete(user);
    }

    public UpdateUserDto updateUser(Long id, UpdateUserDto updateUserDto) { //유저정보 업데이트
        // 해당하는 유저가 존재하지 않을경우
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(USER_NOT_EXIST));

        user.setName(updateUserDto.getName());
        user.setEmail(updateUserDto.getEmail());
        user.setPhoneNumber(updateUserDto.getPhone());

        return updateUserDto.from(user);
    }
}

