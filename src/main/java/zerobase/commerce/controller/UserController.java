package zerobase.commerce.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.commerce.dto.PwChangeDto;
import zerobase.commerce.dto.SignInDto;
import zerobase.commerce.dto.SignUpDto;
import zerobase.commerce.dto.UpdateUserDto;
import zerobase.commerce.security.JWT;
import zerobase.commerce.service.UserService;

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
    // pw change
    @PutMapping("password")
    public ResponseEntity<String> changeCustomerPassword(
            @RequestHeader(name = AUTH_HEADER) String token,
            @RequestBody PwChangeDto pwChangeDto
    ) {

        userService.changePassword(authService.getIdFromToken(token),pwChangeDto);
        return ResponseEntity.ok("비밀번호 변경이 완료되었습니다.");
    }
    @PutMapping("update")
    public ResponseEntity<UpdateUserDto> updateManager(
            @RequestHeader(name = AUTH_HEADER) String token,
            @RequestBody UpdateUserDto updateUserDto
    ) {
        return ResponseEntity.ok(
                userService.updateUser(
                        authService.getIdFromToken(token),
                        updateUserDto
                )
        );
    }

    @DeleteMapping
    public ResponseEntity<String> deleteManager(
            @RequestHeader(name = AUTH_HEADER) String token
    ) {
        userService.deleteUser(authService.getIdFromToken(token));
        return ResponseEntity.ok("고객 회원 탈퇴가 완료되었습니다.");
    }

    @GetMapping("a")
    public String sign(){

        return "테스트";
    }

}
