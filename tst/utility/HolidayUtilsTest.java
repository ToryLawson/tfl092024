package utility;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class HolidayUtilsTest {

    @Test
    void getHolidaysBetween_givenTwoDatesInSameYear_returnsCorrectHolidays () {
        var actual = HolidayUtils.getHolidaysBetween(
                LocalDate.of(2024, Month.JANUARY, 1),
                LocalDate.of(2024, Month.DECEMBER, 31)
        );
        assertEquals(2, actual.size());
        assertTrue(actual.contains(LocalDate.of(2024, Month.JULY, 4)));
        assertTrue(actual.contains(LocalDate.of(2024, Month.SEPTEMBER, 2)));
    }

    @Test
    void getHolidaysBetween_givenTwoDatesExcludingHolidays_returnsEmptySet () {
        var actual = HolidayUtils.getHolidaysBetween(
                LocalDate.of(2024, Month.JULY, 5),
                LocalDate.of(2024, Month.SEPTEMBER, 1)
        );
        assertEquals(0, actual.size());
    }

    @Test
    void getHolidaysBetween_givenTwoDatesIncludingHolidays_returnsBothHolidays () {
        var actual = HolidayUtils.getHolidaysBetween(
                LocalDate.of(2024, Month.JULY, 4),
                LocalDate.of(2024, Month.SEPTEMBER, 2)
        );
        assertEquals(2, actual.size());
        assertTrue(actual.contains(LocalDate.of(2024, Month.JULY, 4)));
        assertTrue(actual.contains(LocalDate.of(2024, Month.SEPTEMBER, 2)));
    }
}