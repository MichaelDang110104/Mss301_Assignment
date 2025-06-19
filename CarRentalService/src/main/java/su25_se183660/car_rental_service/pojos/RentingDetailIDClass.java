package su25_se183660.car_rental_service.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentingDetailIDClass implements Serializable {
    private Integer rentingTransactionId;
    private Integer carId;
}

