package su25_se183660.car_rental_service.services;

import su25_se183660.car_rental_service.dtos.RentingTransactionDTO;

import java.util.List;
import java.util.Optional;

public interface IRentingService {
    RentingTransactionDTO createTransaction(RentingTransactionDTO dto);

    List<RentingTransactionDTO> getAllTransactions(int customerId);

    Optional<RentingTransactionDTO> getTransactionById(Integer id);
}
