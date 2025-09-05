package mutsa.server.service;

import lombok.RequiredArgsConstructor;
import mutsa.server.domain.Card;
import mutsa.server.domain.Users;
import mutsa.server.dto.request.PatchMypageRequest;
import mutsa.server.dto.response.GetMyPagePayload;
import mutsa.server.repository.CardRepository;
import mutsa.server.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final CardRepository cardRepository;
    private final UsersRepository userRepository;

    public GetMyPagePayload findMain(String nickname){
        List<Card> selectedCards =cardRepository.findByUser_NicknameAndDateIsNull(nickname).orElseThrow();
        List<String>message= selectedCards.stream()
                .map(Card::getList)
                .toList();

        return GetMyPagePayload.builder()
                .cards(message)
                .nickname(nickname)
                .build();
    }

    public GetMyPagePayload patchMain(String nickname, PatchMypageRequest req){
        Users user=userRepository.findByNickname(nickname)
                .orElseThrow();
        List<String> add = req.getAddCards() != null ? req.getAddCards() : List.of();
        List<String> remove = req.getRemoveCards() != null ? req.getRemoveCards() : List.of();

        if (!remove.isEmpty()) {
            cardRepository.deleteByUserAndMessageIn(user, remove);
        }

        for (String msg : add) {
                Card card = Card.builder()
                        .userId(user)
                        .list(msg)
                        .achievement(null)
                        .date(null)
                        .build();
                cardRepository.save(card);
        }

        List<Card> selectedCards =cardRepository.findByUser_NicknameAndDateIsNull(nickname).orElseThrow();
        List<String>message= selectedCards.stream()
                .map(Card::getList)
                .toList();

        return GetMyPagePayload.builder()
                .cards(message)
                .nickname(nickname)
                .build();
    }

}
