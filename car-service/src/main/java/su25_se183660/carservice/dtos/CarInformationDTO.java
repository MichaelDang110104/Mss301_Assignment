package su25_se183660.carservice.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CarInformationDTO {
    private String carName;
    private String carDescription;
    private Integer numberOfDoors;
    private Integer seatingCapacity;
    private String fuelType;
    private Integer year;
    private Integer manufacturerId;
    private Integer supplierId;
    private Byte carStatus;
    private BigDecimal carRentingPricePerDay;
}
