package mutsa.server.service;

import lombok.RequiredArgsConstructor;
import mutsa.server.domain.Card;
import mutsa.server.domain.Users;
import mutsa.server.dto.card.CardAchieve;
import mutsa.server.dto.card.CardList;
import mutsa.server.dto.card.CardListResponse;
import mutsa.server.dto.card.CardListsResponse;
import mutsa.server.repository.CardRepository;
import mutsa.server.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final UsersRepository usersRepository;


    public CardListResponse saveCards(String nickname, CardList cardList){
        List<String> cards = cardList.cards();
        for (String content : cards) {
            Card card = Card.builder()
                    .userId(usersRepository.findByNickname(nickname).orElseThrow())
                    .list(content)
                    .build();
            cardRepository.save(card);
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
    public CardListsResponse completeCards(String nickname, CardList cardList){
        Users users = usersRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 없음: " + nickname));
        for (String listItem : cardList.cards()) {
            Card newCard = Card.builder()
                    .userId(users)
                    .list(listItem)
                    .achievement(true)
                    .date(LocalDate.now())
                    .build();

            cardRepository.save(newCard); // 새로 추가됨
        }
        return new CardListsResponse(HttpStatus.CREATED, "카드 리스트 반영 완료");
    }
}
