package mutsa.server.repository;

import mutsa.server.domain.Card;
import mutsa.server.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    long countByUserIdAndDate(Users user, LocalDate date);
    long countByUserIdAndDateAndAchievement(Users user, LocalDate date, boolean achievement);
}
