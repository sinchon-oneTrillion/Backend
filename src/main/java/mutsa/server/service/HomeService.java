package mutsa.server.service;

import lombok.RequiredArgsConstructor;
import mutsa.server.domain.Card;
import mutsa.server.dto.response.HomePayload;
import mutsa.server.repository.CardRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final CardRepository cardRepository;

    public HomePayload findHome(String nickname){
        LocalDate today=LocalDate.now(ZoneId.of("Asia/Seoul"));
        List<Card> cards = cardRepository.findByUser_NicknameAndDate(nickname, today);

        boolean allAchieved = !cards.isEmpty() && cards.stream().allMatch(Card::getAchievement);
        return HomePayload.builder()
                .isAchieved(allAchieved)
                .build();
    }
}
