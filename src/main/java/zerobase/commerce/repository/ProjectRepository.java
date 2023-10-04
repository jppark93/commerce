package zerobase.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.commerce.domain.User;

@Repository
public interface ProjectRepository extends JpaRepository<User,Long> {

}
