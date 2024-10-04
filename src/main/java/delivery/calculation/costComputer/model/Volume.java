package delivery.calculation.costComputer.model;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class Volume {
    private BigDecimal weight;
    private BigDecimal height;
    private BigDecimal width;
    private BigDecimal length;

    private String voucherCode;
}
