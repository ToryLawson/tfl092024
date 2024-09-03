package utility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;


/**
 * Wrapper for BigDecimal which enforces the HALF_UP rounding mode
 * ***
 * When updating, avoid returning BigDecimal instances as this will circumvent this protection.
 * Currently, this is implemented using domain-style methods (e.g. forNPeriods) rather than operators (e.g. multiply);
 * if the latter turns out to be more desirable/simpler, switch to a JSR-354 implementation like Java Money.
 * For more information see <a href="https://jcp.org/en/jsr/detail?id=354">the JSR-354 page</a>.
 * */
public class Money {
    private final BigDecimal value;

    public Money(String value) {
        this(new BigDecimal(value));
    }

    public Money(BigDecimal value) {
        this.value = value.setScale(2, RoundingMode.HALF_UP);
    }

    public Money forNPeriods(long periods) {
        return new Money(value.multiply(BigDecimal.valueOf(periods)));
    }

    public Money getDiscount(int discountPercent) {
        return new Money(value.multiply(new BigDecimal(discountPercent))
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
    }

    public Money subtract(Money subtrahend) {
        return new Money(value.subtract(subtrahend.value));
    }

    @Override
    public String toString() {
        // TODO: internationalize, though at that point, probably time to use a library, see above
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        return formatter.format(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == Money.class && this.toString().equals(obj.toString());
    }
}
