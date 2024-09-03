package main;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import service.CheckoutService;

import java.time.LocalDate;

/**
 * This wraps invocation of ToolRentalAgreementService with a console client, useful for testing and debugging.
 */
public class ConsoleClient {
    public static void main(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("Checkout").build()
                .defaultHelp(true)
                .description("Generate rental agreement based on supplied arguments.");
        parser.addArgument("--code")
                .help("Specify tool code to rent, e.g. CHNS");
        parser.addArgument("--days")
                .help("Specify length of rental in days, e.g. 10");
        parser.addArgument("--discount")
                .setDefault(0)
                .help("Specify discount in whole numbers, e.g. 20 (which will be interpreted as 20%). Defaults to 0.");
        parser.addArgument("--date")
                .setDefault(LocalDate.now())
                .help("Specify checkout date using yyyy-MM-dd, e.g. 2024-09-01. Defaults to today.");
        parser.addArgument("file").nargs("*")
                .help("File to calculate checksum");

        Namespace ns = null;
        try {
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        String toolCode = "";
        int rentalDays = 0;
        int discountPercent = -1;
        LocalDate checkoutDate = LocalDate.now();

        try {
            toolCode = ns.getString("code");

            var rentalDaysString = ns.getString("days");
            rentalDays = Integer.parseInt(rentalDaysString);

            var discountPercentString = ns.getString("discount");
            discountPercent = Integer.parseInt(discountPercentString);

            var checkoutDateString = ns.getString("date");
            checkoutDate = LocalDate.parse(checkoutDateString);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Could not parse supplied arguments. Run with --help to display usage help.");
            System.exit(1);
        }

        generateAgreement(toolCode, rentalDays, discountPercent, checkoutDate);
    }

    private static void generateAgreement(String toolCode, int rentalDays, int discountPercent, LocalDate checkoutDate) {
        var checkoutService = new CheckoutService();
        var agreement = checkoutService.checkout(toolCode, rentalDays, discountPercent, checkoutDate);

        System.out.println(agreement);
    }
}
