package bt;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class TransactionAnalyze {

    public static BigDecimal getFullIncome(TransactionParserResult transactionParserResult){
        BigDecimal tempIncome = BigDecimal.valueOf(0);
        for (Transaction trans: transactionParserResult.getTransactions()){
            tempIncome = tempIncome.add(trans.getIncome());
        }
        return tempIncome;
    }

    public static BigDecimal getFullExpense(TransactionParserResult transactionParserResult){
        BigDecimal tempExpense = BigDecimal.valueOf(0);
        for (Transaction trans: transactionParserResult.getTransactions()){
            tempExpense = tempExpense.add(trans.getExpense());
        }
        return tempExpense;
    }

    public static void printSortedTransactions(TransactionParserResult transactionParserResult){
        List<Transaction> allTransactions = transactionParserResult.getTransactions();
        allTransactions.stream()
                .sorted(Comparator.comparing(Transaction::getContractor))
                .filter(transaction -> transaction.getExpense().compareTo(BigDecimal.valueOf(0)) == 1)
                .forEach(System.out::println);
    }

}
