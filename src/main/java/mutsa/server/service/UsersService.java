package mutsa.server.service;

import lombok.RequiredArgsConstructor;
import mutsa.server.domain.Users;
import mutsa.server.dto.request.LoginRequest;
import mutsa.server.dto.request.SignUpRequest;
import mutsa.server.dto.response.LoginResponsePayload;
import mutsa.server.dto.response.SignUpResponsePayload;
import mutsa.server.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public SignUpResponsePayload save(@RequestBody SignUpRequest signUpRequest){
        Users newUser=Users.builder()
                .nickname(signUpRequest.getNickname())
                .build();
        usersRepository.save(newUser);

        return SignUpResponsePayload.builder()
                .userId(newUser.getId())
                .build();
    }

    public LoginResponsePayload login(@RequestBody LoginRequest loginRequest){
        String myNickname=loginRequest.getNickname();
        return usersRepository.findByNickname(myNickname)
                .map(user->LoginResponsePayload.builder()
                        .nickname(myNickname)
                        .build())
                .orElseGet(() -> LoginResponsePayload.builder()
                        .nickname(null)
                        .build()
                );
    }
}
