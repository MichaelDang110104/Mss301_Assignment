package su25_se183660.car_rental_service.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentingDetailDTO {
    private Integer carId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
}
