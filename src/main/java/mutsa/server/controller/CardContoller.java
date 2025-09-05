package mutsa.server.controller;

import lombok.RequiredArgsConstructor;
import mutsa.server.domain.Users;
import mutsa.server.dto.card.CardList;
import mutsa.server.dto.card.CardListResponse;
import mutsa.server.dto.card.CardLists;
import mutsa.server.dto.card.CardListsResponse;
import mutsa.server.repository.UsersRepository;
import mutsa.server.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardContoller {
    private final CardService cardService;
    private final UsersRepository usersRepository;

    @PostMapping("/choice/{nickname}")
    public ResponseEntity<CardListResponse> saveCards(
            @PathVariable String nickname,
            @RequestBody CardList cardList
    ) {
        CardListResponse response = cardService.saveCards(nickname, cardList);
        return ResponseEntity
                .status(response.httpStatus())
                .body(response);
    }
    @GetMapping("/{nickname}")
    public ResponseEntity<CardLists> getCards(@PathVariable String nickname){
        List<String> lists = cardService.getCards(nickname);
        Users users = usersRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 없음: " + nickname));
        CardLists response = new CardLists(
                HttpStatus.OK,
                users.getId(),
                lists
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
    @PostMapping("/complete/{nickname}")
    public ResponseEntity<CardListsResponse> completeCards (
            @PathVariable String nickname,
            @RequestBody CardList cardList
    ){
        CardListsResponse response = cardService.completeCards(nickname, cardList);
        return ResponseEntity
                .status(response.httpStatus())
                .body(response);
    }
}
