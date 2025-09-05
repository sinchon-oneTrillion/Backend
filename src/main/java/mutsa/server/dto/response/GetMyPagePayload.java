package mutsa.server.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetMyPagePayload {
    private List<String> cards;
    private String nickname;
}
