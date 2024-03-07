import exceptions.BadInputException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private static final String DATE_FORMAT = "MM/dd/yyyy";
    private static final int DAYS_IN_A_YEAR = 365;
    private static Double baseInterestRate;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    private final Scanner scanner = new Scanner(System.in);

    public void run() {

        // Loop to keep the program running until the user chooses to exit
        while (CURRENT_STATE != State.STOP) {
           switch (CURRENT_STATE){
               case START -> handleStart();
               case NEW_LOAN -> handleNewLoan();
           }

            // Prompt user for input
//            try {
//            // Read user input
//            String userInput = scanner.nextLine();
//
//            var difference = DateUtils.differenceInDays(endDate, startDate);
//             System.out.println("day difference: " + difference);
//            for (long day = 1; day <= difference; day++){
//                System.out.println("day: " + day);
//            }
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
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
        System.out.println("Enter the variables separated by space \n1: Start Date (\"MM/dd/yyyy\"), \n2: End Date (\"MM/dd/yyyy\"), \n3: Loan Amount (number) \n 4. Loan Currency (string) \n5 Base Interest Rate (number) \n6 Margin (number)");
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
        } catch (BadInputException e){
            System.out.println(e.getMessage());
        }
    }
    private void unrecognisedInput(){
        System.out.print("Input not recognised");
        }
    }

