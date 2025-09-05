package mutsa.server.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PatchMypageRequest {
    @JsonProperty("add_cards")
    List<String> addCards;

    @JsonProperty("remove_cards")
    List<String> removeCards;
}
