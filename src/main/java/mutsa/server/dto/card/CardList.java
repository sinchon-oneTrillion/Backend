package mutsa.server.dto.card;

import lombok.Getter;

import java.util.List;

public record CardList(
        List<String> cards
){}
