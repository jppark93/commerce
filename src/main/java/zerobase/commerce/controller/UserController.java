package zerobase.commerce.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.commerce.domain.User;
import zerobase.commerce.dto.PwChangeDto;
import zerobase.commerce.dto.SignInDto;
import zerobase.commerce.dto.SignUpDto;
import zerobase.commerce.dto.UpdateUserDto;
import zerobase.commerce.security.JWT;
import zerobase.commerce.service.UserService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private static final String AUTH_HEADER = "Authorization";

    private final JWT authService;
    private final UserService userService;

    @PostMapping("signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto){

        return ResponseEntity.ok(userService.signUp(signUpDto));
    }

    @PostMapping("signin")
    public ResponseEntity<String> signIn(@RequestBody SignInDto signInDto){

        return ResponseEntity.ok(userService.signIn(signInDto));
    }

    @GetMapping("info")
    public ResponseEntity<String> getUserInfo( @RequestHeader(name = AUTH_HEADER,required = false) String token){
        if (token == null) {
            // 'Authorization' 헤더가 누락된 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 존재하지 않음");
        }

        Optional<User> user = userService.getUser(authService.getIdFromToken(token));

        if (user.isPresent()) {
            // 유효한 토큰으로 사용자 정보 가져오기
            return ResponseEntity.ok("회원 정보 가져오기");
        } else {
            // 토큰이 유효하지 않은 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
        }
    }
    // pw change
    @PutMapping("password")
    public ResponseEntity<String> changePassword(
            @RequestHeader(name = AUTH_HEADER) String token,
            @RequestBody PwChangeDto pwChangeDto
    ) {
        Optional<User> user = userService.getUser(authService.getIdFromToken(token));

        if (user.isPresent()) {
            // 유효한 토큰으로 사용자 정보 가져오기
            userService.changePassword(authService.getIdFromToken(token),pwChangeDto);
            return ResponseEntity.ok("비밀번호 변경이 완료되었습니다.");
        }else {
            // 토큰이 유효하지 않은 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
        }

    }


    @PutMapping("update")
    public ResponseEntity<?> updateUser(
            @RequestHeader(name = AUTH_HEADER) String token,
            @RequestBody UpdateUserDto updateUserDto
    ) {
        Optional<User> user = userService.getUser(authService.getIdFromToken(token));
        if (user.isPresent()) {
            // 유효한 토큰으로 사용자 정보 가져오기
            return ResponseEntity.ok(
                    userService.updateUser(
                            authService.getIdFromToken(token),
                            updateUserDto
                    )
            );
        }else {
            // 토큰이 유효하지 않은 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
        }


    }


    @DeleteMapping("delete")
    public ResponseEntity<String> deleteUser(
            @RequestHeader(name = AUTH_HEADER) String token
    ) {
        if (token == null) {
            // 'Authorization' 헤더가 누락된 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 존재하지 않음");
        }
        Optional<User> user = userService.getUser(authService.getIdFromToken(token));
        if (user.isPresent()) {
            userService.deleteUser(authService.getIdFromToken(token));
            return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
        }else {
            // 토큰이 유효하지 않은 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
        }

    }



    @GetMapping("a")
    public String sign(){

        return "테스트";
    }

}
