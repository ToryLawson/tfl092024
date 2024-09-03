package service;

import data.ToolRepository;
import domain.RentalAgreement;

import java.time.LocalDate;

/**
 * The CheckoutService class is responsible for generating a RentalAgreement based on the supplied arguments.
 * If a tool repository is not supplied, it will instantiate one.
 *
 * TODO: replace constructor override with dependency injection
 */
public class CheckoutService {
    private final ToolRepository toolRepository;

    public CheckoutService() {
        this(new ToolRepository());
    }

    private CheckoutService(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    /**
     * Generates a RentalAgreement based on the supplied arguments.
     *
     * @param toolCode          the code of the tool to be rented
     * @param rentalDays        the number of rental days; must be greater than 0
     * @param discountPercent   the discount percentage to be applied to the rental charge, an int from 0 to 100
     * @param checkoutDate      the date of checkout
     * @return                  a RentalAgreement object representing the rental agreement
     * @throws IllegalArgumentException if any of the arguments are invalid
     */
    public RentalAgreement checkout(String toolCode,
                         int rentalDays,
                         int discountPercent,
                         LocalDate checkoutDate) {
        // validate arguments
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Number of rental days must be 1 or greater");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100");
        }
        if (toolCode == null || toolCode.isEmpty()) {
            throw new IllegalArgumentException("Tool code cannot be null or empty");
        }

        var tool = toolRepository.getToolByCode(toolCode);
        return new RentalAgreement(tool, rentalDays, checkoutDate, discountPercent);
    }
}
