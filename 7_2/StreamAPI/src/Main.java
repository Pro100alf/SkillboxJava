import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    private static String staffFile = "data/staff.txt";
    private static String dateFormat = "dd.MM.yyyy";

    public static void main(String[] args) {
        ArrayList<Employee> staff = loadStaffFromFile();

        staff.stream()
                .filter(e -> convertToLocalDateTimeViaInstant(e.getWorkStart()).getYear() == 2017)
                .max(Comparator.comparing(Employee::getSalary))
                .ifPresent(System.out::println);

        staff.stream()
                .filter(e -> convertToLocalDateTimeViaInstant(e.getWorkStart()).getYear() == 2017)
                .map(s -> s.getSalary())
                .reduce((s1, s2) -> s1 + s2)
                .ifPresent(System.out::println);
    }

    private static ArrayList<Employee> loadStaffFromFile() {
        ArrayList<Employee> staff = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(staffFile));
            for (String line : lines) {
                String[] fragments = line.split("\t");
                if (fragments.length != 3) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                staff.add(new Employee(
                        fragments[0],
                        Integer.parseInt(fragments[1]),
                        (new SimpleDateFormat(dateFormat)).parse(fragments[2])
                ));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return staff;
    }

    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }


    public static Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}