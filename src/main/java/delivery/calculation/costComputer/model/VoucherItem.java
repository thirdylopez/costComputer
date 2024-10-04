package delivery.calculation.costComputer.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VoucherItem {
        private String code = null;

        private Float discount = null;

        private LocalDate expiry = null;

        private String error;
}
