package mutsa.server.repository;

import jakarta.transaction.Transactional;
import mutsa.server.domain.Card;
import mutsa.server.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByUser_NicknameAndDateIsNull(String nickname);

    @Transactional
    void deleteByUserAndMessageIn(Users user, Collection<String> messages);
}
