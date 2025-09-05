package mutsa.server.controller;

import lombok.RequiredArgsConstructor;
import mutsa.server.dto.CalendarDetailResponse;
import mutsa.server.dto.CalendarEntry;
import mutsa.server.dto.CalendarRegisterDto;
import mutsa.server.service.CalendarService;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/month/{nickname}")
    public ResponseEntity<List<CalendarEntry>> getUserCalendar(@PathVariable String nickname, @RequestParam LocalDate date) {
        List<CalendarEntry> response = calendarService.getCalendarWithMonth(nickname, date);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{nickname}")
    public ResponseEntity<CalendarRegisterDto> registerCalendar(
            @PathVariable String nickname,
            @RequestBody CalendarRegisterDto request) {
        CalendarRegisterDto response = calendarService.registerCalendar(nickname, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{nickname}")
    public ResponseEntity<CalendarDetailResponse> getDateCalendar(@PathVariable String nickname, @RequestParam LocalDate date) {
        CalendarDetailResponse response = calendarService.getCalendarWithDate(nickname, date);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{nickname}")
    public ResponseEntity<CalendarRegisterDto> modifyCalendar(
            @PathVariable String nickname,
            @RequestBody CalendarRegisterDto request) {
        CalendarRegisterDto response = calendarService.modifyRegister(nickname, request);
        return ResponseEntity.ok(response);
    }

}
