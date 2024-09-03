package utility;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import static java.time.temporal.TemporalAdjusters.firstInMonth;

public class HolidayUtils {
    public static Set<LocalDate> getHolidaysBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("StartDate cannot be after endDate");
        }
        Set<LocalDate> holidays = new HashSet<>();
        for (var year = startDate.getYear(); year <= endDate.getYear(); year++) {
            var independenceDay = getDateDefinedHolidayDate(LocalDate.of(year, Month.JULY, 4));
            if (fallsBetween(independenceDay, startDate, endDate)) {
                holidays.add(independenceDay);
            }
            var laborDay = getScheduleDefinedHolidayDate(year, Month.SEPTEMBER, DayOfWeek.MONDAY, 1);
            if (fallsBetween(independenceDay, startDate, endDate)) {
                holidays.add(laborDay);
            }
        }
        return holidays;
    }

    private static boolean fallsBetween(LocalDate candidateDate, LocalDate startDate, LocalDate endDate) {
        return candidateDate.isAfter(startDate.minusDays(1))
                && candidateDate.isBefore(endDate.plusDays(1));
    }

    private static LocalDate getDateDefinedHolidayDate(LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
            return date.minusDays(1);
        }
        if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return date.plusDays(1);
        }
        return date;
    }

    private static LocalDate getScheduleDefinedHolidayDate(int year, Month month, DayOfWeek dayOfWeek, int weekNumber) {
        var date = LocalDate.of(year, month, 1);
        date = date.with(firstInMonth(dayOfWeek)).plusDays((weekNumber - 1) * 7L);
        return date;
    }
}
