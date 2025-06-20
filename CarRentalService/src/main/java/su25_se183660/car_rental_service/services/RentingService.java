package su25_se183660.car_rental_service.services;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import su25_se183660.car_rental_service.dtos.CarInformationDTO;
import su25_se183660.car_rental_service.dtos.RentingDetailDTO;
import su25_se183660.car_rental_service.dtos.RentingTransactionDTO;
import su25_se183660.car_rental_service.pojos.RentingDetail;
import su25_se183660.car_rental_service.pojos.RentingTransaction;
import su25_se183660.car_rental_service.repositories.IRentingDetailRepo;
import su25_se183660.car_rental_service.repositories.IRentingTransactionRepo;
import su25_se183660.car_rental_service.utils.RentingTransactionMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RentingService implements IRentingService {

    private final IRentingTransactionRepo rentingTransactionRepo;

    private final IRentingDetailRepo rentingDetailRepo;

    private final RestTemplate restTemplate;

    @Override
    @Transactional
    public RentingTransactionDTO createTransaction(RentingTransactionDTO dto) {
        RentingTransaction transaction = RentingTransactionMapper.toEntity(dto);
        RentingTransaction savedTransaction = rentingTransactionRepo.save(transaction);
        for (RentingDetailDTO rentingDetail : dto.getRentingDetails()) {
            String url = "http://car-service/api/cars/get-car/{id}";
            CarInformationDTO car = (CarInformationDTO) restTemplate.getForObject(url, CarInformationDTO.class, rentingDetail.getCarId());
            if (car == null) {
                throw new NotFoundException("Car id is not exist");
            }
        }
        List<RentingDetail> detailList = RentingTransactionMapper.toDetailEntities(dto.getRentingDetails(), savedTransaction.getRentingTransationID());
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
        return rentingTransactionRepo.findById(id).map(tx -> {
            List<RentingDetail> details = rentingDetailRepo.findByRentingTransactionId(id);
            return RentingTransactionMapper.toDto(tx, details);
        });
    }
}

