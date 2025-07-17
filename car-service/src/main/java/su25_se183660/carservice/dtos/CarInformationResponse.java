package su25_se183660.carservice.dtos;

import lombok.Builder;
import lombok.Data;
import su25_se183660.carservice.pojos.Manufacturer;
import su25_se183660.carservice.pojos.Supplier;

import java.math.BigDecimal;

@Data
@Builder
public class CarInformationResponse {
    private Integer carId;
    private String carName;
    private String carDescription;
    private Integer numberOfDoors;
    private Integer seatingCapacity;
    private String fuelType;
    private Integer year;
    private Manufacturer manufacturer;
    private Supplier supplier;
    private Byte carStatus;
    private BigDecimal carRentingPricePerDay;
}
