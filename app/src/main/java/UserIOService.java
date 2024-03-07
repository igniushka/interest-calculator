import exceptions.BadInputException;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class UserIOService {
    private static final Scanner scanner = new Scanner(System.in);
    public static LoanVariables parseLoanVariables(String userInput) throws BadInputException{
        String[] variables = userInput.split("\\s+");
        if (variables.length != 6){
            throw new BadInputException("Invalid number of arguments provided.");
        }
        try{
            Date startDate = DateUtils.parseDate(variables[0]);
            Date endDate = DateUtils.parseDate(variables[1]);
            Long loan = Long.parseLong(variables[2]);
            String currency = variables[3];
            Double baseInterestRate = Double.parseDouble(variables[4]);
            Double marginRate = Double.parseDouble(variables[5]);
            return new LoanVariables(startDate, endDate, loan, currency, baseInterestRate, marginRate);
        } catch (ParseException e) {
            throw new BadInputException("Could not parse start or end dates.");
        } catch (NumberFormatException e){
            throw new BadInputException("Base interest and margin rates need to be type double");
        }
    }

    static String onStart(){
        System.out.println();
        System.out.println("To calculate a new loan type 'n' \nTo view loan history type 'h' \nto quit type 'q'");
        return scanner.nextLine();
    }
    static String onNewLoan(){
        System.out.println();
        System.out.printf("""
                Enter the variables separated by space\s
                1: Start Date ("%s"),\s
                2: End Date ("%s"),\s
                3: Loan Amount (number)\s
                4: Loan Currency (string)\s
                5 Base Interest Rate (number)\s
                6 Margin (number)%n""", Constants.DATE_FORMAT, Constants.DATE_FORMAT);
        System.out.println("Alternatively,to view loan history type 'h' to quit type 'q'");
        return scanner.nextLine();
    }

    static String onHistoryView(){
        System.out.println();
        System.out.println("Enter the record id that you want to run again.");
        System.out.println("Alternatively, to create a new loan type 'n' to quit type 'q'");
        return scanner.nextLine();
    }

    static void printInterestResult(LoanVariables loanVariables){
        System.out.println();
        System.out.println("+------------+---------+----------------+----------------+----------------+----------+");
        System.out.println("| Accrual    | Days    | Daily Interest | Daily Interest | Total Interest | Currency |");
        System.out.println("| Date       | Elapsed | Without Margin | Accrued        | Over Period    |          |");
        System.out.println("+------------+---------+----------------+----------------+----------------+----------+");

        var days = DateUtils.differenceInDays(loanVariables.startDate(), loanVariables.endDate());
        var dailyInterest = loanVariables.loan() * ((loanVariables.baseInterestRate() / 100) / Constants.DAYS_IN_A_YEAR);
        var dailyMargin = loanVariables.loan() * ((loanVariables.marginRate() / 100) / Constants.DAYS_IN_A_YEAR);
        var date = (Date) loanVariables.startDate();

        for (long day = 1; day <= days; day++){
            var accruedDailyInterest = day * dailyInterest;
            var totalInterestOverPeriod = day * (dailyInterest + dailyMargin);
            DateUtils.incrementDay(date);
            System.out.printf("| %-10s | %-7s | %-14.2f | %-14.2f | %-14.2f | %-8s |\n",
                    DateUtils.formattedDate(date),
                    day,
                    dailyInterest,
                    accruedDailyInterest,
                    totalInterestOverPeriod,
                    loanVariables.currency());
            System.out.println("+------------+---------+----------------+----------------+----------------+----------+");
        }
    }

    static void unrecognisedInput(){
        System.out.println();
        System.out.println("Input not recognised");
    }

}
