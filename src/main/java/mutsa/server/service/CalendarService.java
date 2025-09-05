package mutsa.server.service;

import lombok.RequiredArgsConstructor;
import mutsa.server.domain.Calendar;
import mutsa.server.domain.Users;
import mutsa.server.dto.CalendarDetailResponse;
import mutsa.server.dto.CalendarEntry;
import mutsa.server.dto.CalendarRegisterDto;
import mutsa.server.repository.CalendarRepository;
import mutsa.server.repository.CardRepository;
import mutsa.server.repository.UsersRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final UsersRepository usersRepository;
    private final CardService cardService;

    private static boolean toBool(String v) {
        return v != null && !v.isBlank();
    }

    public List<CalendarEntry> getCalendarWithMonth(String nickname, LocalDate date) {

        // 1. 입력 검증
        if (date == null) {
            throw new IllegalArgumentException("date는 null 일 수 없습니다. (예: 2025-09-06)");
        }

        // 2. 유저 찾기
        Users user = usersRepository.findByNickname(nickname).orElseThrow(
                ()->new RuntimeException("닉네임에 해당하는 유저가 없습니다."));

        LocalDate start = date.withDayOfMonth(1);
        LocalDate end   = date.withDayOfMonth(date.lengthOfMonth());

        List<Calendar> calendars = calendarRepository
                .findByUserIdAndDateBetweenOrderByDateAsc(user, start, end);

        // 3. date에 해당하는 달의 모든 calendar 데이터 찾기
        List<CalendarEntry> result = new ArrayList<>();
        for (Calendar c : calendars) {
            LocalDate d = c.getDate();

            boolean hasMemo    = toBool(c.getMemo());
            boolean hasPicture = toBool(c.getPicture());
            int achievementRate = cardService.getAchievementRate(user, d);

            result.add(new CalendarEntry(d, hasPicture, hasMemo, achievementRate));
        }

        return result;

    }

    @Transactional
    public CalendarRegisterDto registerCalendar(String nickname, CalendarRegisterDto request) {
        Users user = usersRepository.findByNickname(nickname).orElseThrow(
                ()->new RuntimeException("닉네임에 해당하는 유저가 없습니다."));

        Calendar newCalendar = Calendar.builder()
                .userId(user)
                .picture(request.getPicture())
                .memo(request.getMemo())
                .date(request.getDate())
                .build();

        calendarRepository.save(newCalendar);

        return request;
    }


    public CalendarDetailResponse getCalendarWithDate(String nickname, LocalDate date) {
        Users user = usersRepository.findByNickname(nickname).orElseThrow(
                ()->new RuntimeException("닉네임에 해당하는 유저가 없습니다."));

        Calendar calendar = calendarRepository.findByUserIdAndDate(user, date)
                .orElseThrow(() -> new RuntimeException("해당 유저의 해당 날짜 캘린더가 없습니다."));

        // 달성률 계산
        int achievementRate = cardService.getAchievementRate(user, date);

        return CalendarDetailResponse.builder()
                .picture(calendar.getPicture())
                .memo(calendar.getMemo())
                .date(calendar.getDate())
                .achievementRate(achievementRate)
                .build();
    }

    @Transactional
    public CalendarRegisterDto modifyRegister(String nickname, CalendarRegisterDto request) {

        if (request == null) {
            throw new IllegalArgumentException("request가 null 입니다.");
        }

        Users user = usersRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("닉네임에 해당하는 유저가 없습니다."));

        Calendar calendar = calendarRepository.findByUserIdAndDate(user, request.getDate())
                .orElseThrow(() -> new RuntimeException("해당 유저의 해당 날짜 캘린더가 없습니다."));

        calendar.setPicture(request.getPicture());
        calendar.setMemo(request.getMemo());

        calendarRepository.save(calendar);

        return CalendarRegisterDto.builder()
                .date(calendar.getDate())
                .picture(calendar.getPicture())
                .memo(calendar.getMemo())
                .build();
    }
}
