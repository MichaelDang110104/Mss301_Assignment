package su25_se183660.car_rental_service.pojos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "RentingTransaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentingTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RentingTransationID")
    private Integer rentingTransationID;

    @Column(name = "RentingDate")
    private LocalDate rentingDate;

    @Column(name = "TotalPrice")
    private BigDecimal totalPrice;

    @Column(name = "CustomerID")
    private Integer customerID;

    @Column(name = "RentingStatus")
    private Byte rentingStatus;

}

