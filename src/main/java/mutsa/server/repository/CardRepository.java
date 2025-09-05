package mutsa.server.repository;

import mutsa.server.domain.Card;
import mutsa.server.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT c.list FROM Card c WHERE c.userId.id = :userId")
    List<String> findListByUserId(@Param("userId") Long userId);
    Optional<Card> findByUserIdAndList(Users userId, String list);
}
