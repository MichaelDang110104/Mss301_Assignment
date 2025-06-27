package su25_se183660.car_rental_service.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import su25_se183660.car_rental_service.pojos.RentingDetail;

import java.time.LocalDate;
import java.util.List;

public interface IRentingDetailRepo extends JpaRepository<RentingDetail, Integer> {
    List<RentingDetail> findByRentingTransactionId(int rentingTransactionId);
    List<RentingDetail> findAllByStartDateGreaterThanEqualAndEndDateLessThanEqualOrderByPriceDesc(LocalDate startDateIsGreaterThan, LocalDate endDateIsLessThan);
}
