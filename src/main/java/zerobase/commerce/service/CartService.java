package zerobase.commerce.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.commerce.domain.Cart;
import zerobase.commerce.domain.Product;
import zerobase.commerce.domain.User;
import zerobase.commerce.exception.UserException;
import zerobase.commerce.repository.CartRepository;

import java.util.List;

import static zerobase.commerce.type.ErrorCode.CART_NOT_EXIST;
import static zerobase.commerce.type.ErrorCode.USER_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new UserException(CART_NOT_EXIST));
    }

    public Cart addToCart(User user, Product product, int quantity) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    public void updateCart(Long cartId, int newQuantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(()-> new UserException(CART_NOT_EXIST));
        if (cart != null) {
            cart.setQuantity(newQuantity);
            cartRepository.save(cart);
        }
    }

    public void removeCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
    public void allRemoveCart() {
        cartRepository.deleteAll();
    }
}
