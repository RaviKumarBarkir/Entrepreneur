package util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class SettlementUtils {

    public Pageable buildDefault(int page, int rows, String sortBy, String direction, String defaultSort) {
        if (page == -1) {
            return Pageable.unpaged();
        }
        if (rows == 0) {
            rows = 10;
        }
        Pageable pageable = null;
        if (sortBy == null || "".equals(sortBy)) {
            sortBy = defaultSort;
        }
        if (direction == null || "".equals(direction)) {
            direction = "desc";
        }
        if ("asc".equals(direction)) {
            pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.ASC, sortBy));
        } else {
            pageable = PageRequest.of(page, rows, Sort.by(Sort.Direction.DESC, sortBy));
        }

        return pageable;
    }

    public Sort exportSort(String sortBy, String direction, String defaultSort) {
        if (sortBy == null || "".equals(sortBy)) {
            sortBy = defaultSort;
        }
        if (direction == null || "".equals(direction)) {
            direction = "desc";
        }
        if ("asc".equals(direction)) {
            return Sort.by(Sort.Direction.ASC, sortBy);
        } else {
            return Sort.by(Sort.Direction.DESC, sortBy);
        }
    }

    public int countBusinessDaysBetween(final LocalDate startDate,
                                        final LocalDate endDate,
                                        final Optional<List<LocalDate>> holidays) {
        if (startDate == null && endDate == null) {
            throw new IllegalArgumentException("Expected from and to dates.");
        }
        if (endDate.isBefore(startDate)) {
            return 0;
        }
        Predicate<LocalDate> isHoliday = date -> holidays.isPresent()
                && holidays.get().contains(date);
        Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
                || date.getDayOfWeek() == DayOfWeek.SUNDAY;
        List<LocalDate> businessDays = startDate.datesUntil(endDate)
                .filter(isWeekend.or(isHoliday).negate())
                .collect(Collectors.toList());

        return businessDays.size();
    }
}
