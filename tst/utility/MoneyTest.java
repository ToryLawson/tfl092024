package utility;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    @Test
    void forNPeriods_givenOnePeriod_returnsSameValue() {
        var money = new Money("10.01");
        var actual = money.forNPeriods(1);
        assertEquals(money, actual);
    }

    @Test
    void forNPeriods_givenOneHundredPeriods_returnsExpectedValue() {
        var money = new Money("10.05");
        var actual = money.forNPeriods(100);
        var expected = new Money("1005.00");
        assertEquals(expected, actual);
    }

    @Test
    void forNPeriods_givenZeroPeriods_returnsZeroValue() {
        var money = new Money("10.00");
        var actual = money.forNPeriods(0);
        var expected = new Money("0.00");
        assertEquals(expected, actual);
    }

    @Test
    void getDiscount_givenZeroDiscount_returnsZeroValue() {
        var money = new Money("100.00");

        var actual = money.getDiscount(0);
        var expected = new Money("0.00");
        assertEquals(expected, actual);
    }

    @Test
    void getDiscount_givenOneHundredPercentDiscount_returnsEntireValue() {
        var money = new Money("100.00");

        var actual = money.getDiscount(100);
        var expected = new Money("100.00");
        assertEquals(expected, actual);
    }

    @Test
    void getDiscount_givenDiscount_returnsExpectedValue() {
        var money = new Money("100.00");

        var actual = money.getDiscount(25);
        var expected = new Money("25.00");
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideRoundingTestParameters")
    void getDiscount_properlyRoundsHalfUp(String input, String expected, String message) {
        var inputBigDecimal = new BigDecimal(input).multiply(BigDecimal.valueOf(2));
        var money = new Money(inputBigDecimal);
        var actual = money.getDiscount(50);
        assertEquals(expected, actual.toString(), message);
    }

    @Test
    void subtract_givenTwoValues_returnsExpectedValue() {
        var money1 = new Money("86.75");
        var money2 = new Money("3.09");

        var actual = money1.subtract(money2);
        var expected = new Money("83.66");
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideToStringTestParameters")
    void toString_properly_returnsString(String moneyInput, String expectedResult) {
        var money = new Money(moneyInput);
        assertEquals(expectedResult, money.toString());
    }

    @ParameterizedTest
    @MethodSource("provideRoundingTestParameters")
    void toString_properlyEmploysHalfUpRounding(String input, String expected, String message) {
        var actual = new Money(input);
        assertEquals(expected, actual.toString(), message);
    }

    @Test
    void equals_givenDifferentObject_returnsFalse() {
        var money = new Money("10");
        var notMoney = new Object();
        assertNotEquals(money, notMoney);
    }

    @Test
    void equals_givenNull_returnsFalse() {
        var money = new Money("10");
        assertNotEquals(null, money);
    }

    @Test
    void equals_givenSameInstance_returnsTrue() {
        var money = new Money("10");
        assertEquals(money, money);
    }

    @Test
    void equals_givenDifferentMoney_returnsFalse() {
        var money1 = new Money("10");
        var money2 = new Money("20");
        assertNotEquals(money1, money2);
    }

    @Test
    void equals_givenEquivalentMoney_returnsTrue() {
        var money1 = new Money("10");
        var money2 = new Money("10");
        assertEquals(money1, money2);
    }

    private static Stream<Arguments> provideToStringTestParameters() {
        return Stream.of(
                Arguments.of("23.12", "$23.12"),
                Arguments.of("0", "$0.00"),
                Arguments.of("100000", "$100,000.00"),
                Arguments.of("-33.33", "-$33.33")
        );
    }

    private static Stream<Arguments> provideRoundingTestParameters() {
        return Stream.of(
                Arguments.of("0.025", "$0.03", "RoundingMode could be DOWN, FLOOR, HALF_DOWN, or HALF_EVEN"),
                Arguments.of("0.011", "$0.01", "RoundingMode could be UP OR CEILING")
        );
    }
}
