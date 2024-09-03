package domain;

import lombok.Getter;
import utility.HolidayUtils;
import utility.Money;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Objects;

@Getter
public class RentalAgreement {
    private final Tool tool;
    private final int rentalDays;
    private final LocalDate checkoutDate;
    private final int discountPercent;
    private final long chargeableDays;
    private final LocalDate dueDate;
    private final Money preDiscountCharge;
    private final Money discountAmount;
    private final Money finalCharge;

    public RentalAgreement(Tool tool, int rentalDays, LocalDate checkoutDate, int discountPercent) {
        this.tool = tool;
        this.rentalDays = rentalDays;
        this.checkoutDate = checkoutDate;
        this.discountPercent = discountPercent;
        this.chargeableDays = calculateChargeableDays();
        this.dueDate = checkoutDate.plusDays(rentalDays);
        this.preDiscountCharge = this.tool.rentalTerms().dailyRentalPrice().forNPeriods(chargeableDays);
        this.discountAmount = preDiscountCharge.getDiscount(discountPercent);
        this.finalCharge = preDiscountCharge.subtract(discountAmount);
    }

    private long calculateChargeableDays() {
        var endDate = checkoutDate.plusDays(rentalDays);
        var startDate = checkoutDate.plusDays(1);
        var rentalTerms = tool.rentalTerms();

        var daysToInclude = EnumSet.allOf(DayOfWeek.class);
        if (!rentalTerms.chargedForWeekdays()) {
            daysToInclude.removeAll(EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
        }
        if (!rentalTerms.chargedForWeekends()) {
            daysToInclude.removeAll(EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));
        }

        var rentalDates = startDate.datesUntil(endDate.plusDays(1))
                .filter(date -> daysToInclude.contains(date.getDayOfWeek()));

        if (!rentalTerms.chargedForHolidays()) {
            var holidays = HolidayUtils.getHolidaysBetween(startDate, endDate);
            rentalDates = rentalDates.filter(date -> !holidays.contains(date));
        }

        return rentalDates.count();
    }

    @Override
    public String toString() {
        return RentalForm.create(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != RentalAgreement.class) return false;
        var otherObject = (RentalAgreement) o;
        return rentalDays == otherObject.rentalDays
                && discountPercent == otherObject.discountPercent
                && chargeableDays == otherObject.chargeableDays
                && Objects.equals(tool, otherObject.tool)
                && Objects.equals(checkoutDate, otherObject.checkoutDate)
                && Objects.equals(dueDate, otherObject.dueDate)
                && Objects.equals(preDiscountCharge, otherObject.preDiscountCharge)
                && Objects.equals(discountAmount, otherObject.discountAmount)
                && Objects.equals(finalCharge, otherObject.finalCharge);
    }
}
