package mutsa.server.dto.card;

import org.springframework.http.HttpStatus;

public record CardListResponse (
    HttpStatus httpStatus,
    Long card_count
) {}
