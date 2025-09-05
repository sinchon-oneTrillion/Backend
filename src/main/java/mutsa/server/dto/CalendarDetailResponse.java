package mutsa.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class CalendarDetailResponse {
    private String picture;
    private String memo;
    private LocalDate date;
    @JsonProperty("achievement_rate")Integer achievementRate;
}
