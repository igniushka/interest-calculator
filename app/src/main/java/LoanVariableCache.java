import java.util.ArrayList;

public class LoanVariableCache {
    private ArrayList<LoanVariables> loanHistory =  new ArrayList<>();
    private LoanVariables selectedRecord = null;

    public void pushLoanInput(LoanVariables input){
        loanHistory.add(input);
    }

    public ArrayList<LoanVariables> getAllInputs(){
        return loanHistory;
    }

    public void setSelectedRecord(LoanVariables selectedRecord) {
        this.selectedRecord = selectedRecord;
    }
    public LoanVariables getSelectedRecord() {
        return this.selectedRecord;
    }
}
