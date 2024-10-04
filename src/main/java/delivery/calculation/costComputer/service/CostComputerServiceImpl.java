package delivery.calculation.costComputer.service;

import delivery.calculation.costComputer.model.VoucherItem;
import delivery.calculation.costComputer.utility.VolumeCalculator;
import io.micrometer.common.util.StringUtils;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Objects;

@Slf4j
@Service
public class CostComputerServiceImpl implements CostComputerService {

    private static final String myntServiceURL = "http://localhost:9081/api/demo/voucher/";
    private static final String API_KEY = "apiKey";

    @Autowired
    private WebClient webclient;


    @Override
    public BigDecimal calculateDeliveryCost(BigDecimal weight, BigDecimal height, BigDecimal width, BigDecimal length) {
        if (weight.compareTo(Rules.REJECT.getWeightLimit()) > 0) {
            return Rules.REJECT.calculateCost(BigDecimal.ZERO, BigDecimal.ZERO);
        } else if (weight.compareTo(Rules.HEAVY_PARCEL.getWeightLimit()) > 0) {
            return Rules.HEAVY_PARCEL.calculateCost(weight, BigDecimal.ZERO);
        } else {
            return calculateUsingVolume(height, width, length);
        }
    }

    @Override
    public BigDecimal calculateNewDeliveryCostBasedOnVoucher(BigDecimal deliveryCost, String voucherCode) {
        if (!StringUtils.isBlank(voucherCode)) {
            VoucherItem voucherItem = webclient.get()
                    .uri(myntServiceURL + voucherCode + "&key=" + API_KEY)
                    .retrieve()
                    .bodyToMono(VoucherItem.class)
                    .timeout(Duration.ofSeconds(10))
                    .onErrorResume(TimeoutException.class, error -> {
                        log.error("Timed Out on API Call");
                        return Mono.error(new ReadTimeoutException());
                    })
                    .block();

            return determineDeliveryCostOnAPICall(deliveryCost, voucherItem);
        }
        return deliveryCost;
    }

    private BigDecimal determineDeliveryCostOnAPICall(BigDecimal deliveryCost, VoucherItem voucherItem) {
        if (!Objects.isNull(voucherItem)) {
            if (!Objects.isNull(voucherItem.getError())) {
                log.error(voucherItem.getError());
            } else if (!Objects.isNull(voucherItem.getDiscount())) {
                return deliveryCost.subtract(
                                deliveryCost.multiply(
                                        BigDecimal.valueOf(
                                                voucherItem.getDiscount()).divide(BigDecimal.valueOf(100)))
                        )
                        .setScale(2, RoundingMode.HALF_DOWN);
            }

        }
        return deliveryCost;
    }

    private BigDecimal calculateUsingVolume(BigDecimal height, BigDecimal width, BigDecimal length) {
        BigDecimal volume = VolumeCalculator.volumeCalculator(height, width, length);
        if (volume.compareTo(Rules.SMALL_PARCEL.getVolumeLimit()) < 0) {
            return Rules.SMALL_PARCEL.calculateCost(BigDecimal.ZERO, volume);
        } else if (volume.compareTo(Rules.MEDIUM_PARCEL.getVolumeLimit()) < 0) {
            return Rules.MEDIUM_PARCEL.calculateCost(BigDecimal.ZERO, volume);
        } else {
            return Rules.LARGE_PARCEL.calculateCost(BigDecimal.ZERO, volume);
        }
    }
}
