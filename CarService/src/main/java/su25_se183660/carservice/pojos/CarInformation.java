package su25_se183660.carservice.pojos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "CarInformation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CarID")
    private int carId;

    @Column(name = "CarName", nullable = false, length = 50)
    private String carName;

    @Column(name = "CarDescription", length = 220)
    private String carDescription;

    @Column(name = "NumberOfDoors")
    private Integer numberOfDoors;

    @Column(name = "SeatingCapacity")
    private Integer seatingCapacity;

    @Column(name = "FuelType", length = 20)
    private String fuelType;

    @Column(name = "Year")
    private Integer year;

    @ManyToOne
    @JoinColumn(name = "ManufacturerID", nullable = false)
    @JsonIgnore
    private Manufacturer manufacturer;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "SupplierID", nullable = false)
    private Supplier supplier;

    @Column(name = "CarStatus")
    private Byte carStatus;

    @Column(name = "CarRentingPricePerDay")
    private BigDecimal carRentingPricePerDay;
}

