package su25_se183660.car_rental_service.utils;

import su25_se183660.car_rental_service.dtos.RentingDetailDTO;
import su25_se183660.car_rental_service.dtos.RentingTransactionDTO;
import su25_se183660.car_rental_service.pojos.RentingDetail;
import su25_se183660.car_rental_service.pojos.RentingTransaction;

import java.util.List;
import java.util.stream.Collectors;

public class RentingTransactionMapper {

    public static RentingTransaction toEntity(RentingTransactionDTO dto) {
        RentingTransaction transaction = new RentingTransaction();
        transaction.setRentingDate(dto.getRentingDate());
        transaction.setTotalPrice(dto.getTotalPrice());
        transaction.setCustomerID(dto.getCustomerId());
        transaction.setRentingStatus(dto.getRentingStatus());
        return transaction;
    }

    public static RentingTransactionDTO toDto(RentingTransaction entity, List<RentingDetail> detailList) {
        return RentingTransactionDTO.builder()
                .rentingDate(entity.getRentingDate())
                .totalPrice(entity.getTotalPrice())
                .customerId(entity.getCustomerID())
                .rentingStatus(entity.getRentingStatus())
                .rentingDetails(detailList.stream().map(detail ->
                        RentingDetailDTO.builder()
                                .carId(detail.getCarId())
                                .startDate(detail.getStartDate())
                                .endDate(detail.getEndDate())
                                .price(detail.getPrice())
                                .build()
                ).collect(Collectors.toList()))
                .build();
    }

    public static List<RentingDetail> toDetailEntities(List<RentingDetailDTO> detailDtos, int transactionId) {
        return detailDtos.stream().map(dto ->
                RentingDetail.builder()
                        .rentingTransactionId(transactionId)
                        .carId(dto.getCarId())
                        .startDate(dto.getStartDate())
                        .endDate(dto.getEndDate())
                        .price(dto.getPrice())
                        .build()
        ).collect(Collectors.toList());
    }

    public static List<RentingDetailDTO> toRentingDetailDTOs(List<RentingDetail> rentingDetails) {
        return rentingDetails.stream().map(
                rd ->
                        RentingDetailDTO
                                .builder()
                                .carId(rd.getCarId())
                                .price(rd.getPrice())
                                .startDate(rd.getStartDate())
                                .endDate(rd.getEndDate())
                                .build()
        ).collect(Collectors.toList());
    }
}
