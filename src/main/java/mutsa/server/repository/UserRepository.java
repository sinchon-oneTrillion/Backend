package mutsa.server.repository;

import mutsa.server.domain.Card;
import mutsa.server.domain.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    Users findByUser_Nickname(String nickname);
}
