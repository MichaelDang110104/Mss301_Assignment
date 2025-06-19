package su25_se183660.car_rental_service.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentingTransactionDTO {
    private LocalDate rentingDate;
    private BigDecimal totalPrice;
    private Integer customerId;
    private Byte rentingStatus;
    private List<RentingDetailDTO> rentingDetails;
}