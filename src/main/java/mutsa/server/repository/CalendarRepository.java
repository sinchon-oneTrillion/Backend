package mutsa.server.repository;

import mutsa.server.domain.Calendar;
import mutsa.server.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    Optional<Calendar> findByUserIdAndDate(Users user, LocalDate date);

    List<Calendar> findByUserIdAndDateBetweenOrderByDateAsc(Users user, LocalDate start, LocalDate end);
}