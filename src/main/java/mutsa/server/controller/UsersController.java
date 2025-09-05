package mutsa.server.controller;

import lombok.RequiredArgsConstructor;
import mutsa.server.dto.request.LoginRequest;
import mutsa.server.dto.request.SignUpRequest;
import mutsa.server.dto.response.ApiResponse;
import mutsa.server.dto.response.LoginResponsePayload;
import mutsa.server.dto.response.SignUpResponsePayload;
import mutsa.server.service.UsersService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignUpResponsePayload>> signUp(@RequestBody SignUpRequest signUpRequest){
        SignUpResponsePayload signUpResponsePayload=usersService.save(signUpRequest);
        return ResponseEntity.ok(ApiResponse.ok("닉네임 생성 성공",signUpResponsePayload));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponsePayload>> login(@RequestBody LoginRequest loginRequest){
        LoginResponsePayload loginResponsePayload=usersService.login(loginRequest);
        if (loginResponsePayload.getNickname() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.fail("일치하는 닉네임이 없습니다",loginResponsePayload));
        }else{
            return ResponseEntity.ok(ApiResponse.ok("로그인 성공",loginResponsePayload));
        }
    }
}
