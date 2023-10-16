package zerobase.commerce.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.commerce.domain.Order;
import zerobase.commerce.domain.User;
import zerobase.commerce.dto.OrderDto;
import zerobase.commerce.security.JWT;
import zerobase.commerce.service.OrderService;
import zerobase.commerce.service.UserService;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("order")
public class OrderController {
    private OrderService orderService;

    private static final String AUTH_HEADER = "Authorization";

    private final JWT authService;

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder( @RequestHeader(name = AUTH_HEADER) String token, @RequestBody OrderDto orderRequestDto) {
        try {
            Optional<User> user = userService.getUser(authService.getIdFromToken(token));

            if (user.isPresent()) {
                OrderDto orderDto = orderService.createOrder(orderRequestDto);
                return ResponseEntity.ok(orderDto);
            } else {
                // 토큰이 유효하지 않은 경우 처리
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@RequestHeader(name = AUTH_HEADER) String token, @PathVariable Long orderId) {
        try {
            Optional<User> user = userService.getUser(authService.getIdFromToken(token));

            if (user.isPresent()) {
                orderService.cancelOrder(orderId);
                return ResponseEntity.ok("Order canceled successfully");
            } else {
                // 토큰이 유효하지 않은 경우 처리
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getOrdersForUser(@RequestHeader(name = "Authorization") String token) {

        Optional<User> userOptional = userService.getUser(authService.getIdFromToken(token));
        User user = userOptional.get();
        if (user!=null) {
            List<Order> orders = orderService.getOrdersForUser(user);
            return ResponseEntity.ok(orders);
        } else {
            // 토큰이 유효하지 않은 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
        }

    }
}
