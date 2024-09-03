package service;

import data.ToolRepository;
import domain.RentalForm;
import domain.RentalAgreement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class RentalFormTest {
    private ToolRepository toolRepository;

    @BeforeEach
    void beforeEach() {
        toolRepository = new ToolRepository();
    }

    @Test
    void create_givenValues_returnsExpectedString() {
        var toolToRent = toolRepository.getToolByCode("LADW");    // ladder charges for weekdays and weekends but not holidays
        var rentalAgreement = new RentalAgreement(
                toolToRent, 365, LocalDate.of(2025, Month.JANUARY, 1), 20);
        var expected = String.join(System.lineSeparator(), new String[] {
                "Tool code: LADW",
                "Tool type: Ladder",
                "Tool brand: Werner",
                "Rental days: 365",
                "Check out date: 01-01-2025",
                "Due date: 01-01-2026",
                "Daily rental charge: $1.99",
                "Charge days: 363",
                "Pre-discount charge: $722.37",
                "Discount percent: 20%",
                "Discount amount: $144.47",
                "Final charge: $577.90"});
        var actual = RentalForm.create(rentalAgreement);
        assertEquals(expected, actual);
    }
}