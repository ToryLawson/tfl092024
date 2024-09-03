package service;

import domain.enums.ToolBrand;
import domain.enums.ToolType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.Money;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {
    CheckoutService service;

    @BeforeEach
    void setUp() {
        this.service = new CheckoutService();
    }

    @Test
    void generateAgreement_givenLadderForOneYear_correctlyCalculatesResultWithDiscount() {
        var actualAgreement = service.checkout("LADW", 365, 15, LocalDate.of(2025, Month.JANUARY, 1));
        assertEquals(15, actualAgreement.getDiscountPercent());
        assertEquals(LocalDate.of(2025, Month.JANUARY, 1), actualAgreement.getCheckoutDate());
        assertEquals(365, actualAgreement.getRentalDays());
        assertEquals(363, actualAgreement.getChargeableDays());

        var actualTool = actualAgreement.getTool();
        assertEquals("LADW", actualTool.code());
        assertEquals(ToolType.Ladder, actualTool.type());
        assertEquals(ToolBrand.Werner, actualTool.brand());

        var actualTerms = actualTool.rentalTerms();
        assertEquals(new Money("1.99"), actualTerms.dailyRentalPrice());
        assertTrue(actualTerms.chargedForWeekdays());
        assertTrue(actualTerms.chargedForWeekends());
        assertFalse(actualTerms.chargedForHolidays());
    }

    @Test
    void generateAgreement_givenChainsawForOneYear_correctlyCalculatesResultWithDiscount() {
        var actualAgreement = service.checkout("CHNS", 365, 20, LocalDate.of(2025, Month.JANUARY, 1));
        assertEquals(20, actualAgreement.getDiscountPercent());
        assertEquals(LocalDate.of(2025, Month.JANUARY, 1), actualAgreement.getCheckoutDate());
        assertEquals(365, actualAgreement.getRentalDays());
        assertEquals(261, actualAgreement.getChargeableDays());

        var actualTool = actualAgreement.getTool();
        assertEquals("CHNS", actualTool.code());
        assertEquals(ToolType.Chainsaw, actualTool.type());
        assertEquals(ToolBrand.Stihl, actualTool.brand());

        var actualTerms = actualTool.rentalTerms();
        assertEquals(new Money("1.49"), actualTerms.dailyRentalPrice());
        assertTrue(actualTerms.chargedForWeekdays());
        assertFalse(actualTerms.chargedForWeekends());
        assertTrue(actualTerms.chargedForHolidays());
    }

    @Test
    void generateAgreement_givenDeWaltJackhammerForOneYear_correctlyCalculatesResultWithDiscount() {
        var actualAgreement = service.checkout("JAKD", 365, 10, LocalDate.of(2025, Month.JANUARY, 1));
        assertEquals(10, actualAgreement.getDiscountPercent());
        assertEquals(LocalDate.of(2025, Month.JANUARY, 1), actualAgreement.getCheckoutDate());
        assertEquals(365, actualAgreement.getRentalDays());
        assertEquals(259, actualAgreement.getChargeableDays());

        var actualTool = actualAgreement.getTool();
        assertEquals("JAKD", actualTool.code());
        assertEquals(ToolType.Jackhammer, actualTool.type());
        assertEquals(ToolBrand.DeWalt, actualTool.brand());

        var actualTerms = actualTool.rentalTerms();
        assertEquals(new Money("2.99"), actualTerms.dailyRentalPrice());
        assertTrue(actualTerms.chargedForWeekdays());
        assertFalse(actualTerms.chargedForWeekends());
        assertFalse(actualTerms.chargedForHolidays());
    }

    @Test
    void generateAgreement_givenRidgidJackhammer_correctlyCalculatesResultWithDiscount() {
        var actualAgreement = service.checkout("JAKR", 365, 25, LocalDate.of(2025, Month.JANUARY, 1));
        assertEquals(25, actualAgreement.getDiscountPercent());
        assertEquals(LocalDate.of(2025, Month.JANUARY, 1), actualAgreement.getCheckoutDate());
        assertEquals(365, actualAgreement.getRentalDays());
        assertEquals(259, actualAgreement.getChargeableDays());

        var actualTool = actualAgreement.getTool();
        assertEquals("JAKR", actualTool.code());
        assertEquals(ToolType.Jackhammer, actualTool.type());
        assertEquals(ToolBrand.Ridgid, actualTool.brand());

        var actualTerms = actualTool.rentalTerms();
        assertEquals(new Money("2.99"), actualTerms.dailyRentalPrice());
        assertTrue(actualTerms.chargedForWeekdays());
        assertFalse(actualTerms.chargedForWeekends());
        assertFalse(actualTerms.chargedForHolidays());
    }
    @Test
    void checkout_givenNullToolCode_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.checkout(null, 5, 10, LocalDate.now()));
    }

    @Test
    void checkout_givenEmptyToolCode_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.checkout("", 5, 10, LocalDate.now()));
    }

    @Test
    void checkout_givenRentalDaysLessThanOne_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.checkout("JAKR", 0, 10, LocalDate.now()));
    }

    @Test
    void checkout_givenDiscountPercentLessThanZero_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.checkout("JAKR", 5, -1, LocalDate.now()));
    }

    @Test
    void checkout_givenDiscountPercentMoreThanHundred_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> service.checkout("JAKR", 5, 101, LocalDate.now()));
    }
}
