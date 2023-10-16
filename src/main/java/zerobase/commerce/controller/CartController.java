package zerobase.commerce.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.commerce.domain.Cart;
import zerobase.commerce.domain.Product;
import zerobase.commerce.domain.User;
import zerobase.commerce.exception.UserException;
import zerobase.commerce.security.JWT;
import zerobase.commerce.service.CartService;
import zerobase.commerce.service.ProductService;
import zerobase.commerce.service.UserService;

import java.util.List;
import java.util.Optional;

import static zerobase.commerce.type.ErrorCode.CART_NOT_EXIST;

@RestController
@RequiredArgsConstructor
@RequestMapping("cart")
public class CartController {
    private final CartService cartService;
    private static final String AUTH_HEADER = "Authorization";

    private final JWT authService;
    private final UserService userService;
    private final ProductService productService;
    @GetMapping("/all")
    public ResponseEntity<List<Cart>> getAllCarts(@RequestHeader(name = AUTH_HEADER) String token) {
        if (token == null) {

            return null;
        }
        Optional<User> userOptional = userService.getUser(authService.getIdFromToken(token));
        List<Cart> carts = cartService.getAllCarts();
        return ResponseEntity.ok(carts);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<?> getCartById(@RequestHeader(name = AUTH_HEADER, required = false) String token, @PathVariable Long cartId) {
        if (token == null) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
        Optional<User> userOptional = userService.getUser(authService.getIdFromToken(token));
        // 토큰 검증 로직: 토큰이 유효한지 확인하는 코드
        if (userOptional.isPresent()) {
            // 토큰이 유효한 경우, 해당 코드 블록 실행
            Cart cart = cartService.getCartById(cartId);
            if (cart != null) {
                return ResponseEntity.ok(cart);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            // 토큰이 유효하지 않은 경우, 인증 실패 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(
            @RequestHeader(name = AUTH_HEADER) String token,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "0") int quantity
    ) {
        // 여기에서 토큰을 사용하여 현재 로그인한 사용자를 식별하고 User 객체를 가져옵니다.

        Optional<User> userOptional = userService.getUser(authService.getIdFromToken(token));

        // Product 객체를 productId를 사용하여 가져옵니다.

        Optional<Product> productOptional = productService.getProduct(productId);

        if (userOptional.isPresent() && productOptional.isPresent()) {
            User user = userOptional.get();
            Product product = productOptional.get();
            Cart cart = cartService.addToCart(user, product, quantity);
            return ResponseEntity.ok(cart);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update/{cartId}")
    public ResponseEntity<?> updateCart(
            @RequestHeader(name = AUTH_HEADER) String token,
            @PathVariable Long cartId,
            @RequestParam int newQuantity
    ) {
        if (token == null) {
            // 토큰이 없는 경우, 인증 실패 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
        Optional<User> userOptional = userService.getUser(authService.getIdFromToken(token));
        // 토큰 검증 로직: 토큰이 유효한지 확인하는 코드
        if (userOptional.isPresent()) {
            cartService.updateCart(cartId, newQuantity);
            return ResponseEntity.ok().build();
        } else {
            // 토큰이 유효하지 않은 경우, 인증 실패 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }

    }

    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<?> removeCart(
            @RequestHeader(name = AUTH_HEADER) String token,
            @PathVariable Long cartId) {
        if (token == null) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
        Optional<User> userOptional = userService.getUser(authService.getIdFromToken(token));
        // 토큰 검증 로직: 토큰이 유효한지 확인하는 코드
        if (userOptional.isPresent()) {
            cartService.removeCart(cartId);
            return ResponseEntity.ok().build();
        } else {
            // 토큰이 유효하지 않은 경우, 인증 실패 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }


    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> allRemoveCart(
            @RequestHeader(name = AUTH_HEADER) String token,
            @PathVariable Long cartId) {
        if (token == null) {
            // 토큰이 없는 경우, 인증 실패 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
        Optional<User> userOptional = userService.getUser(authService.getIdFromToken(token));
        // 토큰 검증 로직: 토큰이 유효한지 확인하는 코드
        if (userOptional.isPresent()) {
            cartService.allRemoveCart();
            return ResponseEntity.ok().build();
        } else {
            // 토큰이 유효하지 않은 경우, 인증 실패 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }


    }
}
