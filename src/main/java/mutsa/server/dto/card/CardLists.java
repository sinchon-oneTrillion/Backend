package mutsa.server.dto.card;

import org.springframework.http.HttpStatus;

import java.util.List;

public record CardLists (
        HttpStatus httpStatus,
        Long userId,
        List<String> cards
)
{}
