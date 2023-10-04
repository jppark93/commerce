package zerobase.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.commerce.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {

    Optional<User> findByUserName(String userName);


    Boolean existsByUserName(String userName);
}
