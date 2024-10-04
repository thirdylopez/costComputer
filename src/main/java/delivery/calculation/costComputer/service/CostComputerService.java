package delivery.calculation.costComputer.service;

import java.math.BigDecimal;

public interface CostComputerService {

    BigDecimal calculateDeliveryCost(BigDecimal weight, BigDecimal height, BigDecimal width, BigDecimal length);

    BigDecimal calculateNewDeliveryCostBasedOnVoucher(BigDecimal deliveryCost, String voucher);
}
