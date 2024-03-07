import java.util.Date;

public record LoanVariables(Date startDate,
                            Date endDate,
                            Long loan,
                            String currency,
                            Double baseInterestRate,
                            Double marginRate) {

}
