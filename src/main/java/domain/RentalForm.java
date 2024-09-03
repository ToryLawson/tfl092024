package domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class RentalForm {
    public static String create(RentalAgreement rentalAgreement) {
        var tool = rentalAgreement.getTool();
        var rentalDays = rentalAgreement.getRentalDays();
        var checkoutDate = rentalAgreement.getCheckoutDate();
        var chargeableDays = rentalAgreement.getChargeableDays();
        var rentalPrice = tool.rentalTerms().dailyRentalPrice();
        var discountPercent = rentalAgreement.getDiscountPercent();

        var preDiscountCharge = rentalPrice.forNPeriods(chargeableDays);
        var discountAmount = preDiscountCharge.getDiscount(discountPercent);

        return "Tool code: %s%n".formatted(tool.code()) +
                "Tool type: %s%n".formatted(tool.type()) +
                "Tool brand: %s%n".formatted(tool.brand()) +
                "Rental days: %s%n".formatted(rentalAgreement.getRentalDays()) +
                "Check out date: %s%n".formatted(formatDate(checkoutDate)) +
                "Due date: %s%n".formatted(formatDate(rentalAgreement.getCheckoutDate().plusDays(rentalDays))) +
                "Daily rental charge: %s%n".formatted(rentalPrice) +
                "Charge days: %s%n".formatted(chargeableDays) +
                "Pre-discount charge: %s%n".formatted(preDiscountCharge) +
                "Discount percent: %s%%%n".formatted(discountPercent) +
                "Discount amount: %s%n".formatted(discountAmount) +
                "Final charge: %s".formatted(preDiscountCharge.subtract(discountAmount));
    }

    private static String formatDate(LocalDate inputDate) {
        return inputDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    }
}
