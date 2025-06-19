package su25_se183660.car_rental_service.pojos;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "RentingDetail")
@IdClass(RentingDetailIDClass.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentingDetail {
    @Id
    @Column(name = "RentingTransactionID")
    private int rentingTransactionId;

    @Column(name = "CarID")
    private int carId;

    @Column(name = "StartDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "EndDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "Price")
    private BigDecimal price;

}

