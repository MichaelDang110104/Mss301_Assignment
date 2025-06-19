package su25_se183660.car_rental_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import su25_se183660.car_rental_service.dtos.RentingTransactionDTO;
import su25_se183660.car_rental_service.pojos.RentingDetail;
import su25_se183660.car_rental_service.pojos.RentingTransaction;
import su25_se183660.car_rental_service.repositories.IRentingDetailRepo;
import su25_se183660.car_rental_service.repositories.IRentingTransactionRepo;
import su25_se183660.car_rental_service.utils.RentingTransactionMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentingService implements IRentingService {

    private final IRentingTransactionRepo rentingTransactionRepo;
    private final IRentingDetailRepo rentingDetailRepo;

    @Override
    @Transactional
    public RentingTransactionDTO createTransaction(RentingTransactionDTO dto) {
        RentingTransaction transaction = RentingTransactionMapper.toEntity(dto);
        RentingTransaction savedTransaction = rentingTransactionRepo.save(transaction);

        List<RentingDetail> detailList = RentingTransactionMapper
                .toDetailEntities(dto.getRentingDetails(), savedTransaction.getRentingTransationID());
        for (RentingDetail detail : detailList) {
            rentingDetailRepo.save(detail);
        }
        return RentingTransactionMapper.toDto(savedTransaction, detailList);
    }

    @Override
    public List<RentingTransactionDTO> getAllTransactions(int customerId) {
        List<RentingTransaction> transactions = rentingTransactionRepo.getAllByCustomerID(customerId);
        return transactions.stream().map(tx -> {
            List<RentingDetail> details = rentingDetailRepo.findByRentingTransactionId((tx.getRentingTransationID()));
            return RentingTransactionMapper.toDto(tx, details);
        }).toList();
    }

    @Override
    public Optional<RentingTransactionDTO> getTransactionById(Integer id) {
        return rentingTransactionRepo.findById(id)
                .map(tx -> {
                    List<RentingDetail> details = rentingDetailRepo.findByRentingTransactionId(id);
                    return RentingTransactionMapper.toDto(tx, details);
                });
    }
}

