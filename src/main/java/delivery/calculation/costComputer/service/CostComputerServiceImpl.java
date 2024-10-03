package delivery.calculation.costComputer.service;

import delivery.calculation.costComputer.utility.VolumeCalculator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CostComputerServiceImpl implements CostComputerService{


    @Override
    public BigDecimal calculateDeliveryCost(BigDecimal weight, BigDecimal height, BigDecimal width, BigDecimal length) {
        if(weight.compareTo(Rules.REJECT.getWeightLimit())>0){
            return Rules.REJECT.calculateCost(BigDecimal.ZERO, BigDecimal.ZERO);
        }else if(weight.compareTo(Rules.HEAVY_PARCEL.getWeightLimit()) > 0){
            return Rules.HEAVY_PARCEL.calculateCost(weight, BigDecimal.ZERO);
        }else{
            return calculateUsingVolume(height, width, length);
        }
    }

    private BigDecimal calculateUsingVolume(BigDecimal height, BigDecimal width, BigDecimal length){
        BigDecimal volume = VolumeCalculator.volumeCalculator(height, width, length);
        if(volume.compareTo(Rules.SMALL_PARCEL.getVolumeLimit())< 0){
            return Rules.SMALL_PARCEL.calculateCost(BigDecimal.ZERO, volume);
        }else if (volume.compareTo(Rules.MEDIUM_PARCEL.getVolumeLimit())< 0){
            return Rules.MEDIUM_PARCEL.calculateCost(BigDecimal.ZERO, volume);
        }else{
            return Rules.LARGE_PARCEL.calculateCost(BigDecimal.ZERO, volume);
        }
    }
}
