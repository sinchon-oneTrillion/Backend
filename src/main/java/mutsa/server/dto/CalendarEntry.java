package mutsa.server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CalendarEntry {
    LocalDate date;
    @JsonProperty("has_picture") Boolean hasPicture;
    @JsonProperty("has_memo") Boolean hasMemo;
    @JsonProperty("achievement_rate")Integer achievementRate;
}
