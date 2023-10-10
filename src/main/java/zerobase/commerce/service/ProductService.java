package zerobase.commerce.service;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import zerobase.commerce.domain.Product;
import zerobase.commerce.dto.ProductDto;
import zerobase.commerce.exception.UserException;
import zerobase.commerce.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

import static zerobase.commerce.type.ErrorCode.PRODUCT_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProjectRepository productRepository;

    public Product productReg(ProductDto productDto) throws UserException {

        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());

        return productRepository.save(product);
    }
    public Optional<Product> getProduct(Long id) throws UserException {

        return productRepository.findById(id);
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Long productId, String name, int price) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();
            existingProduct.setName(name);
            existingProduct.setPrice(price);
            return productRepository.save(existingProduct);
        } else {
            throw new UserException(PRODUCT_NOT_EXIST); // 상품이 없을 때 예외 처리
        }
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
