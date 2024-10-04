package delivery.calculation.costComputer.controller;

import delivery.calculation.costComputer.model.Volume;
import delivery.calculation.costComputer.service.CostComputerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
public class CostCalculatorController {

    public static final String COST_COMPUTER_PATH = "api/v1/costComputer";

    private final CostComputerService costComputerService;

    @PostMapping(COST_COMPUTER_PATH)
    public BigDecimal calculateDeliveryCost(@RequestBody Volume volume){
        BigDecimal initialDeliveryCost = costComputerService.calculateDeliveryCost(volume.getWeight(), volume.getHeight(), volume.getWidth(), volume.getLength());

        return costComputerService.calculateNewDeliveryCostBasedOnVoucher(initialDeliveryCost, volume.getVoucherCode());
    }
}
