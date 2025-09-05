package mutsa.server.controller;

import lombok.RequiredArgsConstructor;
import mutsa.server.dto.response.ApiResponse;
import mutsa.server.dto.response.HomePayload;
import mutsa.server.service.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping()
    public ResponseEntity<ApiResponse<HomePayload>> getHome(@PathVariable String nickname){
        HomePayload homePayload=homeService.findHome(nickname);
        return ResponseEntity.ok(ApiResponse.ok("홈페이지 성공",homePayload));
    }
}
