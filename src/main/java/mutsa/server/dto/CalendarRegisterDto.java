package mutsa.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class CalendarRegisterDto {
    private String picture;
    private String memo;
    private LocalDate date;
}
