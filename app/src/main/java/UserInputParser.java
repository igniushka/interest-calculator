import exceptions.BadInputException;

import java.text.ParseException;
import java.util.Date;

public class UserInputParser {
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
}
