package su25_se183660.car_rental_service.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ResponseRentingDetailDTO {
    private CarInformationDTO car;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal price;
}
