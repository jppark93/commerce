package zerobase.commerce.domain;


import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.asm.Advice;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import zerobase.commerce.type.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "total_price")
    private int totalPrice;

    @CreatedDate
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
