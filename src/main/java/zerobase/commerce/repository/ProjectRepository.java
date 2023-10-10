package zerobase.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.commerce.domain.Product;


@Repository
public interface ProjectRepository extends JpaRepository<Product,Long> {

}
