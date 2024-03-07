import exceptions.BadInputException;

public class InterestCalculatorMachine {
    private enum State {
        START,
        NEW_LOAN,
        LOAN_HISTORY,
        EDIT_EXISTING,

        STOP
    }
    private static State CURRENT_STATE = State.START;
    public void run() {

        // Loop to keep the program running until the user chooses to exit
        while (CURRENT_STATE != State.STOP) {
           switch (CURRENT_STATE){
               case START -> handleStart();
               case NEW_LOAN -> handleNewLoan();
               case LOAN_HISTORY -> handleLoanHistory();

           }
        }
    }

    private void handleStart(){
        var userInput = UserIOService.onStart();
        switch (userInput){
            case "n" -> CURRENT_STATE = State.NEW_LOAN;
            case "h" -> CURRENT_STATE = State.LOAN_HISTORY;
            case "q" -> CURRENT_STATE = State.STOP;
            default -> UserIOService.unrecognisedInput();
        }
    }
    private void handleNewLoan() {
        var userInput = UserIOService.onNewLoan();
        switch (userInput) {
            case "h" -> CURRENT_STATE = State.LOAN_HISTORY;
            case "q" -> CURRENT_STATE = State.STOP;
            default -> handleLoanInput(userInput);
        }
    }

    private void handleLoanHistory() {
        var userInput = UserIOService.onNewLoan();
        switch (userInput) {
            case "n" -> CURRENT_STATE = State.NEW_LOAN;
            case "q" -> CURRENT_STATE = State.STOP;
            default -> handleLoanInput(userInput);
        }
    }

    private void handleLoanInput(String userInput){
        try {
            LoanVariables loanVariables = UserIOService.parseLoanVariables(userInput);
            UserIOService.printInterestResult(loanVariables);
            CURRENT_STATE = State.START;
        } catch (BadInputException e){
            System.out.println(e.getMessage());
        }
    }
}

