package su25_se183660.car_rental_service.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import su25_se183660.car_rental_service.pojos.RentingTransaction;

import java.util.List;

public interface IRentingTransactionRepo extends JpaRepository<RentingTransaction, Integer> {
    List<RentingTransaction> getAllByCustomerID(Integer customerID);
}
