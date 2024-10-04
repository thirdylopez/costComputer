package delivery.calculation.costComputer.utility;

import java.math.BigDecimal;

public class VolumeCalculator {

    private VolumeCalculator() {

    }

    public static BigDecimal volumeCalculator(BigDecimal height, BigDecimal width, BigDecimal length) {
        return height.multiply(width).multiply(length);

    }
}
