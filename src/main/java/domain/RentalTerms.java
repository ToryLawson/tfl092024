package domain;

import domain.enums.ToolType;
import utility.Money;

public record RentalTerms(ToolType toolType, Money dailyRentalPrice, boolean chargedForWeekdays,
                          boolean chargedForWeekends, boolean chargedForHolidays) {
}
