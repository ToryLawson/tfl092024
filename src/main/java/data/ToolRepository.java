package data;

import domain.RentalTerms;
import domain.Tool;
import domain.enums.ToolBrand;
import domain.enums.ToolType;
import utility.Money;

import java.util.Map;

import static java.util.Map.entry;

/**
 * Rough example of the data model. Here the data layer is wrapped in the repository class
 * and accessed using a single domain method, "getToolByCode".
 */
public class ToolRepository {
    public Tool getToolByCode(String toolCode) {
        var tool = toolsByCode.get(toolCode);
        if (tool == null) {
            throw new IllegalArgumentException("Tool " + toolCode + " not found in repository");
        }
        return tool;
    }

    /*
     Roughly represents TOOL_TYPE_RENTAL_TERMS database view
     */
    private final Map<ToolType, RentalTerms> rentalChargesByToolType = Map.ofEntries(
            entry(ToolType.Chainsaw, new RentalTerms(ToolType.Chainsaw, new Money("1.49"), true, false, true)),
            entry(ToolType.Ladder, new RentalTerms(ToolType.Ladder, new Money("1.99"), true, true, false)),
            entry(ToolType.Jackhammer, new RentalTerms(ToolType.Jackhammer, new Money("2.99"), true, false, false))
    );

    /*
     Roughly represents TOOL database table
     */
    private final Map<String, Tool> toolsByCode = Map.ofEntries(
            entry("CHNS", new Tool(ToolBrand.Stihl, "CHNS", ToolType.Chainsaw, rentalChargesByToolType.get(ToolType.Chainsaw))),
            entry("LADW", new Tool(ToolBrand.Werner, "LADW", ToolType.Ladder, rentalChargesByToolType.get(ToolType.Ladder))),
            entry("JAKD", new Tool(ToolBrand.DeWalt, "JAKD", ToolType.Jackhammer, rentalChargesByToolType.get(ToolType.Jackhammer))),
            entry("JAKR", new Tool(ToolBrand.Ridgid, "JAKR", ToolType.Jackhammer, rentalChargesByToolType.get(ToolType.Jackhammer)))
    );
}
