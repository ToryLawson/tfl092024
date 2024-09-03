package data;

import domain.enums.ToolBrand;
import domain.enums.ToolType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToolRepositoryTest {
    private ToolRepository toolRepository;

    @BeforeEach
    void beforeEach() {
        toolRepository = new ToolRepository();
    }

    @Test
    void getToolByCode_whenGivenValidCode_returnsCorrectToolWithRentalTerms() {
        var validCode = "JAKD";

        var actualTool = toolRepository.getToolByCode(validCode);

        assertEquals(actualTool.code(), validCode);
        assertEquals(actualTool.brand(), ToolBrand.DeWalt);
        assertEquals(actualTool.type(), ToolType.Jackhammer);
        assertTrue(actualTool.rentalTerms().chargedForWeekdays());
        assertFalse(actualTool.rentalTerms().chargedForWeekends());
        assertFalse(actualTool.rentalTerms().chargedForHolidays());
    }

    @Test
    void getToolByCode_whenGivenInvalidCode_throwsCorrectException() {
        var invalidCode = "NOPE";

        assertThrows(IllegalArgumentException.class, () -> toolRepository.getToolByCode(invalidCode));
    }
}