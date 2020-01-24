package bt;

import java.util.ArrayList;
import java.util.List;

public class TransactionParserResult {

    private List<Transaction> transactions;
    private List<String> notValidLines;

    public TransactionParserResult(){
        transactions = new ArrayList<>();
        notValidLines = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }

    public void addNotValidLine(String line){
        notValidLines.add(line);
    }

    public List<Transaction> getTransactions(){
        return transactions;
    }

    public List<String> getNotValidLines(){
        return notValidLines;
    }

}
