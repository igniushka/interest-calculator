import java.util.Date;

public record LoanVariables(Date startDate,
                            Date endDate,
                            Double loan,
                            String currency,
                            Double baseInterestRate,
                            Double marginRate) {
}
