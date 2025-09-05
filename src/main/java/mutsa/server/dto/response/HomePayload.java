package mutsa.server.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HomePayload {
    @JsonProperty("is_achieved")
    private Boolean isAchieved;
}
