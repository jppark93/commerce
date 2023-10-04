package zerobase.commerce.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.commerce.dto.SignInDto;
import zerobase.commerce.dto.SignUpDto;
import zerobase.commerce.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    @PostMapping("signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto){

        return ResponseEntity.ok(userService.signUp(signUpDto));
    }

    @PostMapping("signin")
    public ResponseEntity<String> signIn(@RequestBody SignInDto signInDto){

        return ResponseEntity.ok(userService.signIn(signInDto));
    }

    @GetMapping("a")
    public String sign(){

        return "테스트";
    }

}
