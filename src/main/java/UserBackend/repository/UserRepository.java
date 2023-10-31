package UserBackend.repository;

import UserBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findUserById(Long id);
    List<User> findAllByRole(String role);
}
