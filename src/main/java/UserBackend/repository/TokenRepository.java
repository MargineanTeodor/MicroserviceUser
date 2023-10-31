package UserBackend.repository;

import UserBackend.model.Token;
import UserBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {

}
