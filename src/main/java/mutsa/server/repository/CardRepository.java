package mutsa.server.repository;

import jakarta.transaction.Transactional;
import mutsa.server.domain.Card;
import mutsa.server.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByUser_NicknameAndDateIsNull(String nickname);

    @Transactional
    void deleteByUserAndMessageIn(Users user, Collection<String> messages);
    @Query("SELECT c.list FROM Card c WHERE c.userId.id = :userId")
    List<String> findListByUserId(@Param("userId") Long userId);
    Optional<Card> findByUserIdAndList(Users userId, String list);
}
