package mutsa.server.dto.card;

import org.springframework.http.HttpStatus;

import java.util.List;

public record CardTrueFalse(
        HttpStatus httpStatus,
        Long userId,
        List<CardAchieve> cards
) {
}
