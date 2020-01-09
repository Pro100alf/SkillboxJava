package employees;

import companies.Company;

import java.math.BigDecimal;

public class TopManager implements Employee {

    final private BigDecimal BONUS_50P = BigDecimal.valueOf(0.5);
    final private BigDecimal BONUS_THRESHOLD = BigDecimal.valueOf(10000000);
    private BigDecimal salary;
    private Company curCompany;

    public TopManager(BigDecimal fixedSalary, Company company){
        salary = fixedSalary;
        curCompany = company;
    }

    @Override
    public BigDecimal getMonthSalary() {
        BigDecimal curSalary = salary;
        if (curCompany.getIncome().compareTo(BONUS_THRESHOLD) > 0){
            curSalary = salary.add(salary.multiply(BONUS_50P));
        }
        return curSalary;
    }
}
