package mutsa.server.service;

import lombok.RequiredArgsConstructor;
import mutsa.server.domain.Card;
import mutsa.server.domain.Users;
import mutsa.server.dto.response.HomePayload;
import mutsa.server.repository.CardRepository;
import mutsa.server.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final CardRepository cardRepository;
    private final UsersRepository usersRepository;

    public HomePayload findHome(String nickname){
        Users user=usersRepository.findByNickname(nickname)
                .orElseThrow();

        LocalDate today=LocalDate.now(ZoneId.of("Asia/Seoul"));
        List<Card> cards = cardRepository.findByUserIdAndDate(user.getId(), today);

        boolean allAchieved = !cards.isEmpty() && cards.stream().allMatch(Card::getAchievement);
        return HomePayload.builder()
                .isAchieved(allAchieved)
                .build();
    }
}
