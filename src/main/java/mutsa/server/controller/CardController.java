package mutsa.server.controller;

import lombok.RequiredArgsConstructor;
import mutsa.server.domain.Users;
import mutsa.server.dto.card.*;
import mutsa.server.repository.UsersRepository;
import mutsa.server.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {
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
