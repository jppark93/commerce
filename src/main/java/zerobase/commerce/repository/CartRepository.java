package zerobase.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.commerce.domain.Cart;
import zerobase.commerce.domain.Product;

import java.util.List;

@Repository
public interface CartRepository  extends JpaRepository<Cart,Long> {

}
