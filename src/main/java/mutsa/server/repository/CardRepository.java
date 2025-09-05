package mutsa.server.repository;

import mutsa.server.domain.Card;
import mutsa.server.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    long countByUserAndDate(Users user, LocalDate date);
    long countByUserAndDateAndAchievement(Users user, LocalDate date, boolean achievement);
}
