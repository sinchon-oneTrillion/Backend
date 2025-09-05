package mutsa.server.controller;

import lombok.RequiredArgsConstructor;
import mutsa.server.domain.Users;
import mutsa.server.dto.card.CardAchieve;
import mutsa.server.dto.card.CardLists;
import mutsa.server.dto.request.PatchMypageRequest;
import mutsa.server.dto.response.ApiResponse;
import mutsa.server.dto.response.GetMyPagePayload;
import mutsa.server.repository.UsersRepository;
import mutsa.server.service.CardService;
import mutsa.server.service.MyPageService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;
    private final CardService cardService;
    private final UsersRepository usersRepository;

//    @GetMapping("/{nickname}")
//    public ResponseEntity<ApiResponse<GetMyPagePayload>> getMain(@PathVariable String nickname){
//        GetMyPagePayload getMyPagePayload=myPageService.findMain(nickname);
//        return ResponseEntity.ok(ApiResponse.ok("마이페이지 조회 완료",getMyPagePayload));
//    }

@GetMapping("/{nickname}")
public ResponseEntity<CardLists> getCards(@PathVariable String nickname){
    Users users = usersRepository.findByNickname(nickname)
            .orElseThrow(() -> new IllegalArgumentException("해당 유저 없음: " + nickname));
    List<CardAchieve> cards = cardService.getCards(nickname);
    CardLists response = new CardLists(
            HttpStatus.OK,
            users.getId(),
            cards
    );

    return ResponseEntity.status(HttpStatus.OK).body(response);
}

    @PatchMapping("/{nickname}")
    public ResponseEntity<ApiResponse<GetMyPagePayload>> patchMain(@PathVariable String nickname, @RequestBody PatchMypageRequest rep){
        GetMyPagePayload getMyPagePayload=myPageService.patchMain(nickname,rep);
        return ResponseEntity.ok(ApiResponse.ok("마이페이지 수정 완료",getMyPagePayload));
    }


}
