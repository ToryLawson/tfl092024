package domain;

import domain.enums.ToolBrand;
import domain.enums.ToolType;

public record Tool(ToolBrand brand, String code, ToolType type, RentalTerms rentalTerms) {
}
