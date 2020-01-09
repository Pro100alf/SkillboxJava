package employees;

import java.math.BigDecimal;

public class Manager implements Employee {

    static private final BigDecimal PERCENT_5 = BigDecimal.valueOf(0.5);
    private BigDecimal salary;

    public Manager(BigDecimal fixedSalary, BigDecimal earnedMoney){
        salary = fixedSalary.add(earnedMoney.multiply(PERCENT_5));
    }

    @Override
    public BigDecimal getMonthSalary() {
        return salary;
    }
}
