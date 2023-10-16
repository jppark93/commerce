package zerobase.commerce.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.commerce.domain.Order;
import zerobase.commerce.type.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private String address;
    private List<OrderItemDto> orderItems;
    public OrderDto(Order order) {
        orderId = order.getId();
        name = order.getUser().getName();
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        address = order.getAddress();
        orderItems = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDto(orderItem))
                .collect(toList());
    }
}
