package delivery.calculation.costComputer.service;

import delivery.calculation.costComputer.controller.RejectItemException;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
@Getter
public enum Rules {
    REJECT(java.math.BigDecimal.valueOf(50.0), java.math.BigDecimal.ZERO){
        @Override
        public BigDecimal calculateCost(BigDecimal inputWeight, BigDecimal inputVolume) {
            throw new RejectItemException();
        }
    },
    HEAVY_PARCEL(BigDecimal.valueOf(10.0), BigDecimal.ZERO){
        @Override
        public BigDecimal calculateCost(BigDecimal inputWeight, BigDecimal inputVolume) {
            return inputWeight.multiply(BigDecimal.valueOf(20.0)).setScale(2, RoundingMode.HALF_DOWN);
        }
    },
    SMALL_PARCEL(BigDecimal.ZERO, BigDecimal.valueOf(1500.0)){
        @Override
        public BigDecimal calculateCost(BigDecimal inputWeight, BigDecimal inputVolume) {
            return inputVolume.multiply(BigDecimal.valueOf(0.03)).setScale(2, RoundingMode.HALF_DOWN);
        }
    },
    MEDIUM_PARCEL(BigDecimal.ZERO, BigDecimal.valueOf(2500.0)){
        @Override
        public BigDecimal calculateCost(BigDecimal inputWeight, BigDecimal inputVolume) {
            return  inputVolume.multiply(BigDecimal.valueOf(0.04)).setScale(2, RoundingMode.HALF_DOWN);
        }
    },
    LARGE_PARCEL(BigDecimal.ZERO, BigDecimal.ZERO){
        @Override
        public BigDecimal calculateCost(BigDecimal inputWeight, BigDecimal inputVolume) {
            return inputVolume.multiply(BigDecimal.valueOf(0.05)).setScale(2, RoundingMode.HALF_DOWN);
        }
    };

    private final BigDecimal weightLimit;
    private final BigDecimal volumeLimit;

    Rules(BigDecimal weightLimit, BigDecimal volumeLimit){
        this.weightLimit = weightLimit;
        this.volumeLimit = volumeLimit;
    }
    public abstract BigDecimal calculateCost(BigDecimal inputWeight, BigDecimal inputVolume);

}
