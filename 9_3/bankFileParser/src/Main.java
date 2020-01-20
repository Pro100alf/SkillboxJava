import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static String path = "data/movementList.csv";

    public static void main(String[] args) {
        BigDecimal fullIncome = BigDecimal.valueOf(0);
        BigDecimal fullExpenses = BigDecimal.valueOf(0);
        List<String> expensesList = new ArrayList<>();
        try{
            List<String> allLines = Files.readAllLines(Paths.get(path));
            allLines.remove(0);
            for (String line: allLines){
                String[] fragments = line.split(",", 8);
                fullIncome = fullIncome.add(stringToBigDecimal(fragments[6]));
                BigDecimal tempExpenses = stringToBigDecimal(fragments[7]);
                fullExpenses = fullExpenses.add(tempExpenses);
                if (tempExpenses.compareTo(BigDecimal.valueOf(0)) == 1){
                    expensesList.add(line);
                }
            }
        }
        catch(Exception ex){
            ex.getStackTrace();
            System.out.println(ex.getMessage());
        }
        System.out.println("Общие доходы: " + fullIncome.toString() + " руб");
        System.out.println("Общие расходы: " + fullExpenses.toString() + " руб");
        System.out.println("Отсортированные расходы: ");
        expensesList.stream().sorted(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String tempO1 = o1.split(",", 8)[5].split("  +")[1];
                String tempO2 = o2.split(",", 8)[5].split("  +")[1];
                return tempO1.compareTo(tempO2);
            }
        }).forEach(System.out::println);
    }

    private static BigDecimal stringToBigDecimal(String val){
        return BigDecimal.valueOf(Double.parseDouble(val.replace("\"", "").replace(",", ".")));
    }

}
