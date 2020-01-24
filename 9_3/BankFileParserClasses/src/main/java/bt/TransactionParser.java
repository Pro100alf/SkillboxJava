package bt;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;



public final class TransactionParser {

    private static final int CURRENCY_INDEX = 2;
    private static final int DATE_INDEX = 3;
    private static final int DESCRIPTION_INDEX = 5;
    private static final int CONTRACTOR_INDEX = 1;
    private static final int MCCCODE_INDEX = 4;
    private static final int STARTSPLITDESCRIPTION_LENGTH = 2;
    private static final int ENDSPLITDESCRIPTION_LENGTH = 5;
    private static final int INCOME_INDEX = 6;
    private static final int EXPENSE_INDEX = 7;
    private static final int DATA_LENGTH = 8;
    private static final int SUBSTRING_INDEX = 70;
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.yy");

    public static TransactionParserResult TransactionParserParse(String path){
        TransactionParserResult transactionParserResult = new TransactionParserResult();
        try{
            List<String> allLines = Files.readAllLines(Paths.get(path));
            allLines.remove(0); //remove file description
            for (String line: allLines){
                String[] splitLine = line.split(",", DATA_LENGTH);
                if (splitLine.length == 8){
                    Currency currency = Currency.valueOf(splitLine[CURRENCY_INDEX]);
                    LocalDate date = LocalDate.parse(splitLine[DATE_INDEX], DTF);
                    BigDecimal income = stringToBigDecimal(splitLine[INCOME_INDEX]);
                    BigDecimal expense = stringToBigDecimal(splitLine[EXPENSE_INDEX]);
                    String startSplitDescription = splitLine[DESCRIPTION_INDEX].substring(0, SUBSTRING_INDEX);
                    String endSplitDescription = splitLine[DESCRIPTION_INDEX].substring(SUBSTRING_INDEX + 1);
                    String contractor = startSplitDescription.split(" ", STARTSPLITDESCRIPTION_LENGTH)[CONTRACTOR_INDEX].trim();
                    String MccCode = endSplitDescription.split(" +", ENDSPLITDESCRIPTION_LENGTH)[MCCCODE_INDEX];
                    transactionParserResult.addTransaction(new Transaction(currency, date, income, expense, contractor, MccCode));
                }
                else{
                    transactionParserResult.addNotValidLine(line);
                    throw new IllegalArgumentException("Wrong line parameters");
                }
            }

        }
        catch(Exception ex){
            ex.getStackTrace();
            System.out.println(ex);
        }
        return transactionParserResult;
    }

    private static BigDecimal stringToBigDecimal(String val){
        return BigDecimal.valueOf(Double.parseDouble(val.replace("\"", "").replace(",", ".")));
    }
}
