import java.util.Date;

public record LoanVariables(Date startDate,
                            Date endDate,
                            Double loan,
                            String currency,
                            Double baseInterestRate,
                            Double marginRate) {
    public LoanVariables setStartDate(Date newStartDate){
        return new LoanVariables(newStartDate, endDate, loan, currency, baseInterestRate, marginRate);
    }

    public LoanVariables setEndDate(Date newEndDate){
        return new LoanVariables(startDate, newEndDate, loan, currency, baseInterestRate, marginRate);
    }

    public LoanVariables setLoan(Double newLoan){
        return new LoanVariables(startDate, endDate, newLoan, currency, baseInterestRate, marginRate);
    }

    public LoanVariables setCurrency(String newCurrency){
        return new LoanVariables(startDate, endDate, loan, newCurrency, baseInterestRate, marginRate);
    }

    public LoanVariables setBaseInterestRate(Double newBaseInterestRate){
        return new LoanVariables(startDate, endDate, loan, currency, newBaseInterestRate, marginRate);
    }

    public LoanVariables setMarginRate(Double newMarginRate){
        return new LoanVariables(startDate, endDate, loan, currency, baseInterestRate, newMarginRate);
    }

}
