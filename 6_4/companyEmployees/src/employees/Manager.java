package employees;

import java.math.BigDecimal;

public class Manager implements Employee, IncomeReceivable{

    static private final BigDecimal PERCENT_5 = BigDecimal.valueOf(0.5);
    private BigDecimal salary;
    private BigDecimal earnedMoney;

    public Manager(BigDecimal fixedSalary, BigDecimal earnedMoney){
        salary = fixedSalary.add(earnedMoney.multiply(PERCENT_5));
        this.earnedMoney = earnedMoney;
    }

    @Override
    public BigDecimal getMonthSalary() {
        return salary;
    }

    @Override
    public BigDecimal getIncome() {
        return earnedMoney;
    }
}
