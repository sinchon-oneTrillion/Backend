package mutsa.server.service;

import lombok.RequiredArgsConstructor;
import mutsa.server.domain.Card;
import mutsa.server.domain.Users;
import mutsa.server.dto.card.*;
import mutsa.server.repository.CardRepository;
import mutsa.server.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final UsersRepository usersRepository;


    public CardListResponse saveCards(String nickname, CardList cardList){
        List<String> cards = cardList.cards();
        for (String content : cards) {
            Card originalCard = Card.builder()
                    .userId(usersRepository.findByNickname(nickname).orElseThrow())
                    .list(content)
                    .build();
            cardRepository.save(originalCard);
            Card datedCard = Card.builder()
                    .userId(usersRepository.findByNickname(nickname).orElseThrow())
                    .list(content)
                    .date(LocalDate.now())
                    .build();
            cardRepository.save(datedCard);
        }

        return new CardListResponse(HttpStatus.CREATED, (long) cards.size());
    }
    public List<CardAchieve> getCards(String nickname){
        Users users = usersRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 없음: " + nickname));
        List<Card> cards = cardRepository.findAllByUserId_IdAndDateIsNull(users.getId());
        return cards.stream()
                .map(card -> new CardAchieve(
                        card.getList(),
                        Boolean.TRUE.equals(card.getAchievement())
                ))
                .collect(Collectors.toList());
    }
    public CardTrueFalse getCard(String nickname) {
        Users user = usersRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 없음: " + nickname));
        // 1. 날짜 있는 카드만 가져오기
        List<Card> datedCards = cardRepository.findAllByUserId_IdAndDateIsNotNull(user.getId());
        // 2. list 기준으로 그룹핑하여 achieve=true가 하나라도 있으면 true
        Map<String, Boolean> listAchieveMap = datedCards.stream()
                .collect(Collectors.toMap(
                        Card::getList,
                        card -> Boolean.TRUE.equals(card.getAchievement()),
                        (existing, replacement) -> existing || replacement // 하나라도 true면 true
                ));
        // 3. CardAchieve 리스트로 변환
        List<CardAchieve> cards = listAchieveMap.entrySet().stream()
                .map(entry -> new CardAchieve(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return new CardTrueFalse(HttpStatus.OK, user.getId(), cards);
    }
    public CardListsResponse completeCards(String nickname, CardList cardList) {
        Users users = usersRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 없음: " + nickname));
        for (String listItem : cardList.cards()) {
            // 기존 카드 중: userId 일치 && list 일치 && date가 null 인 카드 조회
            Optional<Card> optionalCard = cardRepository
                    .findByUserId_IdAndListAndDateIsNotNull(users.getId(), listItem);
            if (optionalCard.isPresent()) {
                Card card = optionalCard.get();
                card.setAchievement(true);
                card.setDate(LocalDate.now());
                cardRepository.save(card);
            } else {
                System.out.println("카드 없음 또는 이미 완료된 카드: " + listItem);
            }
        }

        return new CardListsResponse(HttpStatus.CREATED, "카드 리스트 반영 완료");
    }
    public int getAchievementRate(Users user, LocalDate date) {
        long total = cardRepository.countByUserIdAndDate(user, date);
        if (total == 0) return 0;

        long achieved = cardRepository.countByUserIdAndDateAndAchievement(user, date, true);
        return (int) ((achieved * 100) / total); // 소수점 삭제

    }
}
