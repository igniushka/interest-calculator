import java.util.ArrayList;

public class LoanCache {
    private final ArrayList<LoanVariables> loanHistory;
    private LoanVariables selectedRecord;
    public LoanCache(){
        this.loanHistory = new ArrayList<>();
        this.selectedRecord = null;
    }

    public void pushLoanInput(LoanVariables input){
        loanHistory.add(input);
    }

    public ArrayList<LoanVariables> getHistory(){
        return loanHistory;
    }

    public void setSelectedRecord(LoanVariables selectedRecord) {
        this.selectedRecord = selectedRecord;
    }
    public LoanVariables getSelectedRecord() {
        return this.selectedRecord;
    }
}
