package mutsa.server.controller;

import lombok.RequiredArgsConstructor;
import mutsa.server.dto.request.PatchMypageRequest;
import mutsa.server.dto.response.ApiResponse;
import mutsa.server.dto.response.GetMyPagePayload;
import mutsa.server.service.MyPageService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping("/{nickname}")
    public ResponseEntity<ApiResponse<GetMyPagePayload>> getMain(@PathVariable String nickname){
        GetMyPagePayload getMyPagePayload=myPageService.findMain(nickname);
        return ResponseEntity.ok(ApiResponse.ok("마이페이지 조회 완료",getMyPagePayload));
    }

    @PatchMapping("/{nickname}")
    public ResponseEntity<ApiResponse<GetMyPagePayload>> patchMain(@PathVariable String nickname, @RequestBody PatchMypageRequest rep){
        GetMyPagePayload getMyPagePayload=myPageService.patchMain(nickname,rep);
        return ResponseEntity.ok(ApiResponse.ok("마이페이지 수정 완료",getMyPagePayload));
    }

}
