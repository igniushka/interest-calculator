import exceptions.BadInputException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class InterestCalculatorMachine {
    private enum State {
        START,
        NEW_LOAN,
        LOAN_HISTORY,
        EDIT_EXISTING,

        STOP
    }
    private static State CURRENT_STATE = State.START;
    private static final int DAYS_IN_A_YEAR = 365;
    private final Scanner scanner = new Scanner(System.in);

    public void run() {

        // Loop to keep the program running until the user chooses to exit
        while (CURRENT_STATE != State.STOP) {
           switch (CURRENT_STATE){
               case START -> handleStart();
               case NEW_LOAN -> handleNewLoan();
           }
        }
    }

    private void handleStart(){
        System.out.println("To calculate a new loan type 'n'\n To view loan history type 'h'\n to quit type 'q'\n:");
        String userInput = scanner.nextLine();
        switch (userInput){
            case "n" -> CURRENT_STATE = State.NEW_LOAN;
            case "h" -> CURRENT_STATE = State.LOAN_HISTORY;
            case "q" -> CURRENT_STATE = State.STOP;
            default -> unrecognisedInput();

        }
    }
    private void handleNewLoan() {
        System.out.printf("Enter the variables separated by space \n1: Start Date (\"%s\"), \n2: End Date (\"%s\"), " +
                "\n3: Loan Amount (number) \n4: Loan Currency (string) \n5 Base Interest Rate (number) " +
                "\n6 Margin (number)%n", Constants.DATE_FORMAT, Constants.DATE_FORMAT);
        System.out.println("Alternatively,to view loan history type 'h' to quit type 'q'");
        String userInput = scanner.nextLine();
        switch (userInput) {
            case "h" -> CURRENT_STATE = State.LOAN_HISTORY;
            case "q" -> CURRENT_STATE = State.STOP;
            default -> handleLoanInput(userInput);
        }
    }

    private void handleLoanInput(String userInput){
        try {
            LoanVariables loanVariables = UserInputParser.parseLoanVariables(userInput);
            printInterestResult(loanVariables);
            CURRENT_STATE = State.START;
        } catch (BadInputException e){
            System.out.println(e.getMessage());
        }
    }

    private void printInterestResult(LoanVariables loanVariables){
        var days = DateUtils.differenceInDays(loanVariables.startDate(), loanVariables.endDate());
        var dailyInterest = loanVariables.loan() * ((loanVariables.baseInterestRate() / 100) / DAYS_IN_A_YEAR);
        var dailyMargin = loanVariables.loan() * ((loanVariables.marginRate() / 100) / DAYS_IN_A_YEAR);
        var date = (Date) loanVariables.startDate();
        for (long day = 1; day <= days; day++){
            DateUtils.incrementDay(date);
            System.out.println("----------------------------------------------");
            System.out.printf("1: Daily interest without margin: %.2f (%s)\n", dailyInterest, loanVariables.currency());
            System.out.printf("2: daily interest accrued: %.2f (%s)\n", day * dailyInterest, loanVariables.currency());
            System.out.printf("3: Accrual date: %s \n", DateUtils.formattedDate(date));
            System.out.printf("4: Number of Days elapsed: %s \n", day);
            System.out.printf("5: total interest over the period: %.2f (%s)\n",  day * (dailyInterest + dailyMargin), loanVariables.currency());
            System.out.println("----------------------------------------------");
        }
    }
    private void unrecognisedInput(){
        System.out.print("Input not recognised");
        }
    }

