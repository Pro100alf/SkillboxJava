import companies.Company;
import employees.Employee;
import employees.Operator;
import employees.Manager;
import employees.TopManager;

import java.math.BigDecimal;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int companyIncome = 15000000;
        Company myFirstCompany = new Company(BigDecimal.valueOf(companyIncome));
        Random random = new Random();
        for (int i = 0; i < 180; i++) {
            int curSalary = random.nextInt(20000) + 40000;
            myFirstCompany.hire(new Operator(BigDecimal.valueOf(curSalary)));
        }
        for (int i = 0; i < 80; i++) {
            int curSalary = random.nextInt(20000) + 20000;
            int curEarnedMoney = random.nextInt(200000) + 10000;
            myFirstCompany.hire(new Manager(BigDecimal.valueOf(curSalary), BigDecimal.valueOf(curEarnedMoney)));
        }
        for (int i = 0; i < 10; i++) {
            int curSalary = random.nextInt(50000) + 60000;
            myFirstCompany.hire(new TopManager(BigDecimal.valueOf(curSalary), myFirstCompany));
        }
        myFirstCompany.getTopSalaryStaff(10);
        myFirstCompany.getLowestSalaryStaff(30);

        myFirstCompany.fire(-1, 135);

        myFirstCompany.getTopSalaryStaff(10);
        myFirstCompany.getLowestSalaryStaff(30);
    }
}
