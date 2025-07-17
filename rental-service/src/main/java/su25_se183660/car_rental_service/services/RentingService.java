package su25_se183660.car_rental_service.services;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import su25_se183660.car_rental_service.dtos.*;
import su25_se183660.car_rental_service.pojos.RentingDetail;
import su25_se183660.car_rental_service.pojos.RentingTransaction;
import su25_se183660.car_rental_service.repositories.IRentingDetailRepo;
import su25_se183660.car_rental_service.repositories.IRentingTransactionRepo;
import su25_se183660.car_rental_service.utils.ApiResponse;
import su25_se183660.car_rental_service.utils.RentingTransactionMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RentingService implements IRentingService {

    private final IRentingTransactionRepo rentingTransactionRepo;

    private final IRentingDetailRepo rentingDetailRepo;

    private final RestTemplate restTemplate;

    @Override
    @Transactional
    public RentingTransactionDTO createTransaction(RentingTransactionDTO dto) {
        String userValidate = "http://user-service/users/get-user/{userID}";
        ResponseEntity<ApiResponse<UserDTO>> userResponse =
                sendRestTemplate(userValidate, new ParameterizedTypeReference<ApiResponse<UserDTO>>() {}, dto.getCustomerId());
        UserDTO userDTO = userResponse.getBody().getData();
        if (userDTO == null) {
            throw new NotFoundException("Cannot find user");
        }
        RentingTransaction transaction = RentingTransactionMapper.toEntity(dto);
        RentingTransaction savedTransaction = rentingTransactionRepo.save(transaction);
        for (RentingDetailDTO rentingDetail : dto.getRentingDetails()) {
            String carValidate = "http://car-service/api/cars/get-car/{id}";
            ResponseEntity<ApiResponse<CarInformationDTO>> carResponse =
                    sendRestTemplate(carValidate, new ParameterizedTypeReference<ApiResponse<CarInformationDTO>>() {}, rentingDetail.getCarId());

            if (carResponse.getBody() == null || carResponse.getBody().getData() == null) {
                throw new NotFoundException("Car id does not exist");
            }
        }
        List<RentingDetail> detailList = RentingTransactionMapper.toDetailEntities(dto.getRentingDetails(), savedTransaction.getRentingTransationID());
        for (RentingDetail detail : detailList) {
            rentingDetailRepo.save(detail);
        }
        return RentingTransactionMapper.toDto(savedTransaction, detailList);
    }


    @Override
    public List<RentingTransactionDTO> getAllTransactions(int customerId, String email) {
        String url = "http://user-service/users/get-user/{userID}";
        ResponseEntity<ApiResponse<UserDTO>> response =
                sendRestTemplate(url, new ParameterizedTypeReference<ApiResponse<UserDTO>>() {}, customerId);

        UserDTO userDTO = response.getBody().getData();
        if (userDTO == null || !email.equals(userDTO.getEmail())) {
            throw new AuthorizationDeniedException("You are not allowed to access this resource");
        }
        List<RentingTransaction> transactions = rentingTransactionRepo.getAllByCustomerID(customerId);
        return transactions.stream().map(tx -> {
            List<RentingDetail> details = rentingDetailRepo.findByRentingTransactionId(tx.getRentingTransationID());
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

    @Override
    public List<ResponseRentingDetailDTO> getReportStatistics(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<RentingDetail> reportStatistics = rentingDetailRepo.findAllByStartDateGreaterThanEqualAndEndDateLessThanEqualOrderByPriceDesc(start, end);
        Function<Integer, CarInformationDTO> carFetcher = carId -> {
            String url = "http://car-service/api/cars/get-car/{id}";
            ResponseEntity<ApiResponse<CarInformationDTO>> response =
                    this.sendRestTemplate(url, new ParameterizedTypeReference<>() {}, carId);
            return response.getBody() != null ? response.getBody().getData() : null;
        };

        return RentingTransactionMapper.toRentingDetailDTOs(reportStatistics,carFetcher);
    }

    public <T> ResponseEntity<ApiResponse<T>> sendRestTemplate(String url, ParameterizedTypeReference<ApiResponse<T>> typeRef, Object... params) {
        return restTemplate.exchange(url, HttpMethod.GET, null, typeRef, params);
    }


}

