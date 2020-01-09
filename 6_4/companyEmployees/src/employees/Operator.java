package employees;

import java.math.BigDecimal;

public class Operator implements Employee {

    private BigDecimal salary;

    public Operator(BigDecimal fixedSalary){
        salary = fixedSalary;
    }

    @Override
    public BigDecimal getMonthSalary() {
        return salary;
    }
}
