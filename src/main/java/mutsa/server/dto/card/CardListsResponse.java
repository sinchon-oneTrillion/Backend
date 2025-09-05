package mutsa.server.dto.card;

import org.springframework.http.HttpStatus;

public record CardListsResponse (
        HttpStatus httpStatus,
        String message
){}
