package mutsa.server.repository;

import jakarta.transaction.Transactional;
import mutsa.server.domain.Card;
import mutsa.server.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
//    Optional<List<Card>> findByUser_NicknameAndDateIsNull(String nickname);

    @Transactional
    void deleteByUserIdAndListIn(Users user, Collection<String> messages);
    List<Card> findAllByUserId_IdAndDateIsNotNull(Long userId);
    List<Card> findAllByUserId_IdAndDateIsNull(Long userId);
    long countByUserIdAndDate(Users user, LocalDate date);
    long countByUserIdAndDateAndAchievement(Users user, LocalDate date, boolean achievement);
    Optional<Card> findByUserId_IdAndListAndDateIsNull(Long userId, String list);

}
