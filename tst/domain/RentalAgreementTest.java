package domain;

import data.ToolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class RentalAgreementTest {
    ToolRepository toolRepository;

    @BeforeEach
    void beforeEach() {
        toolRepository = new ToolRepository();
    }

    @Test
    void rentalAgreement_givenOneWeekRentalWithoutHolidays_correctlyAssignsFields() {
        var tool = toolRepository.getToolByCode("CHNS");
        var actual = new RentalAgreement(tool, 7, LocalDate.of(2024, Month.DECEMBER, 10), 10);

        assertEquals(7, actual.getRentalDays());
        assertEquals(LocalDate.of(2024, Month.DECEMBER, 10), actual.getCheckoutDate());
        assertEquals(5, actual.getChargeableDays());
        assertEquals(10, actual.getDiscountPercent());
    }

    @Test
    void rentalAgreement_givenOneWeekRentalWithHoliday_butHolidaysExcluded_correctlyAssignsFields() {
        var tool = toolRepository.getToolByCode("CHNS");
        var actual = new RentalAgreement(tool, 7, LocalDate.of(2024, Month.JULY, 1), 10);

        assertEquals(7, actual.getRentalDays());
        assertEquals(LocalDate.of(2024, Month.JULY, 1), actual.getCheckoutDate());
        assertEquals(5, actual.getChargeableDays());
        assertEquals(10, actual.getDiscountPercent());
    }

    @Test
    void rentalAgreement_givenOneWeekRentalWithHoliday_withHolidaysIncluded_correctlyAssignsFields() {
        var tool = toolRepository.getToolByCode("JAKR");
        var actual = new RentalAgreement(tool, 7, LocalDate.of(2024, Month.JULY, 1), 10);

        assertEquals(7, actual.getRentalDays());
        assertEquals(LocalDate.of(2024, Month.JULY, 1), actual.getCheckoutDate());
        assertEquals(4, actual.getChargeableDays());
        assertEquals(10, actual.getDiscountPercent());
    }

    @Test
    void testToString() {
        var tool = toolRepository.getToolByCode("JAKR");
        var rentalAgreement = new RentalAgreement(tool, 7, LocalDate.of(2024, Month.JULY, 1), 10);
        var expected = RentalForm.create(rentalAgreement);
        var actual = rentalAgreement.toString();
        assertEquals(expected, actual);
    }

    @Test
    void testEquals_givenDifferentInstancesWithSameValues_returnsTrue() {
        var tool1 = toolRepository.getToolByCode("JAKR");
        var rentalAgreement1 = new RentalAgreement(tool1, 7, LocalDate.of(2024, Month.JULY, 1), 10);

        var tool2 = toolRepository.getToolByCode("JAKR");
        var rentalAgreement2 = new RentalAgreement(tool2, 7, LocalDate.of(2024, Month.JULY, 1), 10);

        assertEquals(rentalAgreement1, rentalAgreement2);
    }

    @Test
    void testEquals_givenSameInstance_returnsTrue() {
        var tool = toolRepository.getToolByCode("JAKR");
        var rentalAgreement = new RentalAgreement(tool, 7, LocalDate.of(2024, Month.JULY, 1), 10);

        assertTrue(rentalAgreement.equals(rentalAgreement));
    }

    @Test
    void testEquals_givenNull_returnsFalse() {
        var tool = toolRepository.getToolByCode("JAKR");
        var rentalAgreement = new RentalAgreement(tool, 7, LocalDate.of(2024, Month.JULY, 1), 10);

        assertFalse(rentalAgreement.equals(null));
    }

    @Test
    void testEquals_givenDifferentClass_returnsFalse() {
        var tool = toolRepository.getToolByCode("JAKR");
        var rentalAgreement = new RentalAgreement(tool, 7, LocalDate.of(2024, Month.JULY, 1), 10);

        assertFalse(rentalAgreement.equals(new Object()));
    }

    @Test
    void testEquals_givenSameValuesDifferentInstances_returnsTrue() {
        var tool1 = toolRepository.getToolByCode("JAKR");
        var rentalAgreement1 = new RentalAgreement(tool1, 7, LocalDate.of(2024, Month.JULY, 1), 10);

        var tool2 = toolRepository.getToolByCode("JAKR");
        var rentalAgreement2 = new RentalAgreement(tool2, 7, LocalDate.of(2024, Month.JULY, 1), 10);

        assertTrue(rentalAgreement1.equals(rentalAgreement2));
    }

    @Test
    void testEquals_givenDifferentValues_returnsFalse() {
        var tool1 = toolRepository.getToolByCode("JAKR");
        var rentalAgreement1 = new RentalAgreement(tool1, 7, LocalDate.of(2024, Month.JULY, 1), 10);

        var tool2 = toolRepository.getToolByCode("JAKR");
        var rentalAgreement2 = new RentalAgreement(tool2, 5, LocalDate.of(2024, Month.AUGUST, 1), 15);

        assertFalse(rentalAgreement1.equals(rentalAgreement2));
    }
}
