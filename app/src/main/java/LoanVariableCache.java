import java.util.ArrayList;

public class LoanVariableCache {
    private ArrayList<LoanVariables> inputs =  new ArrayList<>();

    public void pushLoanInput(LoanVariables input){
        inputs.add(input);
    }

    public ArrayList<LoanVariables> getAllInputs(){
        return inputs;
    }
}
