package bt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@ToString
public class Transaction {

    private Currency currency;
    private LocalDate date;
    private BigDecimal income;
    private BigDecimal expense;
    private String contractor;
    private String MccCode;

}
