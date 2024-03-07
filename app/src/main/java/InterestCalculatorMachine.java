import exceptions.BadInputException;

public class InterestCalculatorMachine {
    private final LoanCache loanCache = new LoanCache();
    private enum State {
        START,
        NEW_LOAN,
        HISTORY_VIEW,
        HISTORY_MENU,
        STOP
    }
    private static State CURRENT_STATE = State.START;
    public void run() {

        // Loop to keep the program running until the user chooses to exit
        while (CURRENT_STATE != State.STOP) {
           switch (CURRENT_STATE){
               case START -> handleStart();
               case NEW_LOAN -> handleNewLoan();
               case HISTORY_VIEW -> handleLoanHistory();
               case HISTORY_MENU -> handleHistoryMenu();

           }
        }
    }

    private void handleStart(){
        var userInput = UserIOService.onStart();
        switch (userInput){
            case "n" -> CURRENT_STATE = State.NEW_LOAN;
            case "h" -> CURRENT_STATE = State.HISTORY_VIEW;
            case "q" -> CURRENT_STATE = State.STOP;
            default -> UserIOService.unrecognisedInput();
        }
    }

    private void handleNewLoan() {
        var userInput = UserIOService.onNewLoan();
        switch (userInput) {
            case "h" -> CURRENT_STATE = State.HISTORY_VIEW;
            case "q" -> CURRENT_STATE = State.STOP;
            default -> handleLoanInput(userInput);
        }
    }

    private void handleLoanInput(String userInput){
        try {
            LoanVariables loanVariables = UserIOService.parseLoanVariables(userInput);
            UserIOService.printInterestResult(loanVariables);
            loanCache.pushLoanInput(loanVariables);
            CURRENT_STATE = State.START;
        } catch (BadInputException e){
            System.out.println(e.getMessage());
        }
    }

    private void handleLoanHistory() {
        UserIOService.displayHistory(loanCache.getHistory());
        CURRENT_STATE = State.HISTORY_MENU;
    }

    private void handleHistoryMenu(){
        var userInput = UserIOService.onHistoryView();
        switch (userInput) {
            case "n" -> CURRENT_STATE = State.NEW_LOAN;
            case "q" -> CURRENT_STATE = State.STOP;
            default -> handleHistoryMenuInput(userInput);
        }
    }

    private void handleHistoryMenuInput(String userInput){
        try {
            LoanVariables loanVariables = UserIOService.parseLoanVariables(userInput);
            UserIOService.printInterestResult(loanVariables);
            CURRENT_STATE = State.START;
        } catch (BadInputException ignored){
            UserIOService.unrecognisedInput();
        }
    }

}

