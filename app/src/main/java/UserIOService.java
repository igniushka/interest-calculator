import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class UserIOService {
    private static final Scanner scanner = new Scanner(System.in);

    static String onStart() {
        System.out.println("\nTo calculate a new loan enter 'n' \nTo view loan history enter 'h' \nto quit enter 'q'\n");
        return scanner.nextLine();
    }

    static String onNewLoan() {
        System.out.printf("""
                        \nEnter the variables separated by space\s
                        1: Start Date ("%s"),\s
                        2: End Date ("%s"),\s
                        3: Loan Amount (number)\s
                        4: Loan Currency (string)\s
                        5: Base Interest Rate Percent (number)\s
                        6: Margin Percent (number)\s
                        Alternatively, to view loan history enter 'h' to quit enter 'q'%n""",
                Constants.DATE_FORMAT, Constants.DATE_FORMAT);
        return scanner.nextLine();
    }

    static String onSelectedVariables(LoanVariables loanVariables) {
        System.out.println();
        System.out.println("+------------+------------+-------------------+----------+---------------+-----------+");
        System.out.println("| Start      | End        | Loan              | Currency | Base Interest | Margin    |");
        System.out.println("| Date       | Date       | Amount            |          | Rate Percent  | Percent   |");
        System.out.println("+------------+------------+-------------------+----------+---------------+-----------+");
        System.out.printf("| %-10s | %-10s | %-17.2f | %-8s | %-13.2f | %-9.2f |\n",
                DateUtils.formattedDate(loanVariables.startDate()),
                DateUtils.formattedDate(loanVariables.endDate()),
                loanVariables.loan(),
                loanVariables.currency(),
                loanVariables.baseInterestRate(),
                loanVariables.marginRate());
        System.out.println("+------------+------------+-------------------+----------+---------------+-----------+");
        System.out.printf("""
                        To run the calculation again enter 'r'.\s
                        To edit the inputs enter the variable index (listed below) and a new value separated by space.\s
                        You can only edit one input at a time.\s
                        1: Start Date ("%s"),\s
                        2: End Date ("%s"),\s
                        3: Loan Amount (number)\s
                        4: Loan Currency (string)\s
                        5 Base Interest Rate Percent (number)\s
                        6 Margin Percent (number)\s
                        Alternatively, to calculate a new loan enter 'n', to view loan history enter 'h', to quit enter 'q'%n""",
                Constants.DATE_FORMAT, Constants.DATE_FORMAT);
        return scanner.nextLine();
    }

    static void displayHistory(ArrayList<LoanVariables> loanVariables) {
        System.out.println();
        System.out.println("+-------+------------+------------+-------------------+----------+---------------+-----------+");
        System.out.println("| Index | Start      | End        | Loan              | Currency | Base Interest | Margin    |");
        System.out.println("|       | Date       | Date       | Amount            |          | Rate Percent  | Percent   |");
        System.out.println("+-------+------------+------------+-------------------+----------+---------------+-----------+");

        for (int index = 1; index <= loanVariables.size(); index++) {
            var variables = loanVariables.get(index - 1);
            System.out.printf("| %-5s | %-10s | %-10s | %-17.2f | %-8s | %-13.2f | %-9.2f |\n",
                    index,
                    DateUtils.formattedDate(variables.startDate()),
                    DateUtils.formattedDate(variables.endDate()),
                    variables.loan(),
                    variables.currency(),
                    variables.baseInterestRate(),
                    variables.marginRate());
            System.out.println("+-------+------------+------------+-------------------+----------+---------------+-----------+");
        }
    }

    static String onHistoryView() {
        System.out.print("""
                Enter the record id that you want to run again.\s
                Alternatively, to create a new loan type 'n' to quit type 'q'\n""");
        return scanner.nextLine();
    }

    static void printInterestResult(LoanVariables loanVariables) {
        System.out.println();
        System.out.println("+------------+---------+----------------+----------------+----------------+----------+");
        System.out.println("| Accrual    | Days    | Daily Interest | Daily Interest | Total Interest | Currency |");
        System.out.println("| Date       | Elapsed | Without Margin | Accrued        | Over Period    |          |");
        System.out.println("+------------+---------+----------------+----------------+----------------+----------+");

        var days = DateUtils.differenceInDays(loanVariables.startDate(), loanVariables.endDate());
        var dailyInterest = loanVariables.loan() * ((loanVariables.baseInterestRate() / 100) / Constants.DAYS_IN_A_YEAR);
        var dailyMargin = loanVariables.loan() * ((loanVariables.marginRate() / 100) / Constants.DAYS_IN_A_YEAR);
        var date = (Date) loanVariables.startDate();

        for (long day = 1; day <= days; day++) {
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

    static void unrecognisedInput(String message) {
        System.out.printf("\nInvalid or not recognised input. %s \n", message);
    }

}
