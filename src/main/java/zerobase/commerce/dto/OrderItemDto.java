package zerobase.commerce.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.commerce.domain.OrderItem;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private String itemName;//상품 명
    private int orderPrice; //주문 가격
    private int count;      //주문 수량

    public OrderItemDto(OrderItem orderItem) {
        itemName = orderItem.getItemName();
        orderPrice = orderItem.getPrice();
        count = orderItem.getQuantity();
    }
}
