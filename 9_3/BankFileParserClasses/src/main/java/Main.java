import bt.TransactionAnalyze;
import bt.TransactionParser;
import bt.TransactionParserResult;

public class Main {
    public static void main(String[] args) {
        String path = "src/main/data/movementList.csv";
        TransactionParserResult transactionParserResult = TransactionParser.TransactionParserParse(path);
        System.out.println("Full income: " + TransactionAnalyze.getFullIncome(transactionParserResult));
        System.out.println("Full expense: " + TransactionAnalyze.getFullExpense(transactionParserResult));
        TransactionAnalyze.printSortedTransactions(transactionParserResult);
    }
}
