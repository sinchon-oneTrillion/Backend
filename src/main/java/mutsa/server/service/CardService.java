package mutsa.server.service;

import lombok.RequiredArgsConstructor;
import mutsa.server.domain.Card;
import mutsa.server.domain.Users;
import mutsa.server.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    public int getAchievementRate(Users user, LocalDate date) {
        long total = cardRepository.countByUserIdAndDate(user, date);
        if (total == 0) return 0;

        long achieved = cardRepository.countByUserIdAndDateAndAchievement(user, date, true);
        return (int) ((achieved * 100) / total); // 소수점 삭제
    }
}
