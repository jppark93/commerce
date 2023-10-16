package zerobase.commerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.commerce.domain.Cart;
import zerobase.commerce.domain.Order;
import zerobase.commerce.domain.User;

import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order,Long> {

    List<Order> findByUser(User user);
}
