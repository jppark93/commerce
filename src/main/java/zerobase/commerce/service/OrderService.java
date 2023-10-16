package zerobase.commerce.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.commerce.domain.Order;
import zerobase.commerce.domain.OrderItem;
import zerobase.commerce.domain.User;
import zerobase.commerce.dto.OrderDto;
import zerobase.commerce.dto.OrderItemDto;
import zerobase.commerce.exception.UserException;
import zerobase.commerce.repository.OrderRepository;
import zerobase.commerce.repository.ProductRepository;
import zerobase.commerce.security.JWT;
import zerobase.commerce.type.OrderStatus;
import java.util.*;
import static zerobase.commerce.type.ErrorCode.ORDER_NOT_EXIST;
import static zerobase.commerce.type.ErrorCode.USER_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    private final UserService userService;


    private final ProductRepository productRepository;

    private JWT authService;
    public OrderDto createOrder(OrderDto orderDto) {
        // 주문(Order) 생성
        Order order = new Order();
        order.setStatus(OrderStatus.ORDER); // 주문 상태를 "주문"으로 설정
        order.setAddress(orderDto.getAddress());
        order.setTotalPrice(calculateTotalPrice(orderDto.getOrderItems()));

        // 주문 상세(OrderItem) 생성
        for (OrderItemDto itemDto : orderDto.getOrderItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setItemName(itemDto.getItemName());
            orderItem.setPrice(itemDto.getOrderPrice());
            orderItem.setQuantity(itemDto.getCount());

            order.addOrderItem(orderItem);
        }

        // 주문 저장
        Order savedOrder = orderRepository.save(order);
        return new OrderDto(savedOrder);
    }

    public void cancelOrder(Long orderId) {
        // 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new UserException(USER_NOT_EXIST));

        // 주문 상태가 "주문"인 경우에만 취소 가능
        if (order.getStatus() == OrderStatus.ORDER) {
            order.setStatus(OrderStatus.CANCEL); // 주문 상태를 "취소"로 변경

        } else {
            throw new UserException(ORDER_NOT_EXIST);
        }

        // 변경된 주문 정보 저장
        orderRepository.save(order);
    }
    public List<Order> getOrdersForUser(User user) {
        return orderRepository.findByUser(user);
    }

    // 주문 상세 별 가격을 합산하여 총 주문 가격을 계산
    private int calculateTotalPrice(List<OrderItemDto> orderItems) {
        return orderItems.stream()
                .mapToInt(item -> item.getOrderPrice() * item.getCount())
                .sum();
    }
}
