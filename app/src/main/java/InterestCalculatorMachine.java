import java.text.ParseException;
import java.util.Date;

public class InterestCalculatorMachine {
    private final LoanCache loanCache = new LoanCache();

    private enum State {
        START,
        NEW_LOAN,
        HISTORY_VIEW,
        HISTORY_MENU,
        RECORD_SELECTED,
        STOP
    }

    private static State CURRENT_STATE = State.START;

    public void run() {
        while (CURRENT_STATE != State.STOP) {
            switch (CURRENT_STATE) {
                case NEW_LOAN -> handleNewLoan();
                case HISTORY_VIEW -> handleLoanHistory();
                case HISTORY_MENU -> handleHistoryMenu();
                case RECORD_SELECTED -> handleSelectedRecord();
                default -> handleStart();
            }
        }
    }

    private void handleStart() {
        var userInput = UserIOService.onStart();
        switch (userInput) {
            case "n" -> CURRENT_STATE = State.NEW_LOAN;
            case "h" -> CURRENT_STATE = State.HISTORY_VIEW;
            case "q" -> CURRENT_STATE = State.STOP;
            default -> UserIOService.unrecognisedInput(userInput);
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

    private void handleLoanInput(String userInput) {
        try {
            LoanVariables loanVariables = parseLoanVariables(userInput);
            UserIOService.printInterestResult(loanVariables);
            loanCache.pushLoanInput(loanVariables);
            CURRENT_STATE = State.START;
        } catch (BadInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleLoanHistory() {
        UserIOService.displayHistory(loanCache.getHistory());
        CURRENT_STATE = State.HISTORY_MENU;
    }

    private void handleSelectedRecord() {
        var userInput = UserIOService.onSelectedVariables(loanCache.getSelectedRecord());
        switch (userInput) {
            case "r" -> runSelectedRecord();
            case "n" -> CURRENT_STATE = State.NEW_LOAN;
            case "h" -> CURRENT_STATE = State.HISTORY_VIEW;
            case "q" -> CURRENT_STATE = State.STOP;
            default -> handleSelectedRowInput(userInput);
        }
    }

    private void runSelectedRecord() {
        try {
            UserIOService.printInterestResult(loanCache.getSelectedRecord());
            loanCache.pushLoanInput(loanCache.getSelectedRecord());
            CURRENT_STATE = State.START;
        } catch (BadInputException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleHistoryMenu() {
        var userInput = UserIOService.onHistoryView();
        switch (userInput) {
            case "n" -> CURRENT_STATE = State.NEW_LOAN;
            case "q" -> CURRENT_STATE = State.STOP;
            default -> handleHistoryMenuInput(userInput);
        }
    }

    private void handleHistoryMenuInput(String userInput) {
        try {
            int index = Integer.parseInt(userInput);
            loanCache.selectByIndex(index - 1);
            CURRENT_STATE = State.RECORD_SELECTED;
        } catch (BadInputException | IndexOutOfBoundsException e) {
            UserIOService.unrecognisedInput(e.getMessage());
        }
    }

    private void handleSelectedRowInput(String userInput) {
        try {
            String[] elements = userInput.split("\\s+");
            if (elements.length != 2) {
                throw new BadInputException("Invalid number of elements provided.");
            }
            var index = Integer.parseInt(elements[0]);
            if (index < 1 || index > 6) {
                throw new BadInputException("Index should be in range 1 - 6.");
            }
            switch (index) {
                case 1 ->
                        loanCache.setSelectedRecord(loanCache.getSelectedRecord().setStartDate(DateUtils.parseDate(elements[1])));
                case 2 ->
                        loanCache.setSelectedRecord(loanCache.getSelectedRecord().setEndDate(DateUtils.parseDate(elements[1])));
                case 3 ->
                        loanCache.setSelectedRecord(loanCache.getSelectedRecord().setLoan(Double.parseDouble(elements[1])));
                case 4 -> loanCache.setSelectedRecord(loanCache.getSelectedRecord().setCurrency(elements[1]));
                case 5 ->
                        loanCache.setSelectedRecord(loanCache.getSelectedRecord().setBaseInterestRate(Double.parseDouble(elements[1])));
                case 6 ->
                        loanCache.setSelectedRecord(loanCache.getSelectedRecord().setMarginRate(Double.parseDouble(elements[1])));
            }
        } catch (Exception e) {
            UserIOService.unrecognisedInput(e.getMessage());
        }
    }


    private LoanVariables parseLoanVariables(String userInput) throws BadInputException {
        String[] variables = userInput.split("\\s+");
        if (variables.length != 6) {
            throw new BadInputException("Invalid number of arguments provided.");
        }
        try {
            Date startDate = DateUtils.parseDate(variables[0]);
            Date endDate = DateUtils.parseDate(variables[1]);
            Double loan = Double.parseDouble(variables[2]);
            String currency = variables[3];
            Double baseInterestRate = Double.parseDouble(variables[4]);
            Double marginRate = Double.parseDouble(variables[5]);
            return new LoanVariables(startDate, endDate, loan, currency, baseInterestRate, marginRate);
        } catch (ParseException e) {
            throw new BadInputException("Could not parse start or end dates.");
        } catch (NumberFormatException e) {
            throw new BadInputException("Base interest and margin rates need to be type double");
        }
    }


}

