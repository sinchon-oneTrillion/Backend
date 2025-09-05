package mutsa.server.service;

import lombok.RequiredArgsConstructor;
import mutsa.server.domain.Card;
import mutsa.server.domain.Users;
import mutsa.server.dto.card.CardList;
import mutsa.server.dto.card.CardListResponse;
import mutsa.server.dto.card.CardListsResponse;
import mutsa.server.repository.CardRepository;
import mutsa.server.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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

    public List<String> getCards(String nickname){
        Users users = usersRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 없음: " + nickname));
        List<String> lists = cardRepository.findListByUserId(users.getId());
        return lists;
    }
    public CardListsResponse completeCards(String nickname, CardList cardList){
        Users users = usersRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 없음: " + nickname));
        for (String listItem : cardList.cards()) {
            Card card = cardRepository.findByUserIdAndList(users, listItem)
                    .orElseThrow(() -> new IllegalArgumentException("해당 카드 없음: " + listItem));
            card.markAsCompleted();
            cardRepository.save(card);
        }
        return new CardListsResponse(HttpStatus.CREATED, "카드 리스트 반영 완료");
    }
}
