package su25_se183660.carservice.utils;

import su25_se183660.carservice.dtos.CarInformationDTO;
import su25_se183660.carservice.dtos.CarInformationResponse;
import su25_se183660.carservice.pojos.CarInformation;
import su25_se183660.carservice.pojos.Manufacturer;
import su25_se183660.carservice.pojos.Supplier;

public class CarInformationMapper {

    public static CarInformationDTO toDto(CarInformation entity) {
        return CarInformationDTO.builder()
                .carName(entity.getCarName())
                .carDescription(entity.getCarDescription())
                .numberOfDoors(entity.getNumberOfDoors())
                .seatingCapacity(entity.getSeatingCapacity())
                .fuelType(entity.getFuelType())
                .year(entity.getYear())
                .manufacturerId(entity.getManufacturer().getManufacturerId())
                .supplierId(entity.getSupplier().getSupplierId())
                .carStatus(entity.getCarStatus())
                .carRentingPricePerDay(entity.getCarRentingPricePerDay())
                .build();
    }

    public static CarInformation toEntity(CarInformationDTO dto, Manufacturer manufacturer, Supplier supplier) {
        return CarInformation.builder()
                .carName(dto.getCarName())
                .carDescription(dto.getCarDescription())
                .numberOfDoors(dto.getNumberOfDoors())
                .seatingCapacity(dto.getSeatingCapacity())
                .fuelType(dto.getFuelType())
                .year(dto.getYear())
                .manufacturer(manufacturer)
                .supplier(supplier)
                .carStatus(dto.getCarStatus())
                .carRentingPricePerDay(dto.getCarRentingPricePerDay())
                .build();
    }

    public static CarInformationResponse toResponse(CarInformation car) {
        return CarInformationResponse.builder()
                .carId(car.getCarId())
                .carName(car.getCarName())
                .carDescription(car.getCarDescription())
                .numberOfDoors(car.getNumberOfDoors())
                .seatingCapacity(car.getSeatingCapacity())
                .fuelType(car.getFuelType())
                .year(car.getYear())
                .manufacturer(car.getManufacturer())
                .supplier(car.getSupplier())
                .carStatus(car.getCarStatus())
                .carRentingPricePerDay(car.getCarRentingPricePerDay())
                .build();
    }


    public static CarInformation updateEntity(CarInformation entity, CarInformationDTO dto, Manufacturer manufacturer, Supplier supplier) {
        entity.setCarName(dto.getCarName());
        entity.setCarDescription(dto.getCarDescription());
        entity.setNumberOfDoors(dto.getNumberOfDoors());
        entity.setSeatingCapacity(dto.getSeatingCapacity());
        entity.setFuelType(dto.getFuelType());
        entity.setYear(dto.getYear());
        entity.setCarStatus(dto.getCarStatus());
        entity.setCarRentingPricePerDay(dto.getCarRentingPricePerDay());
        entity.setManufacturer(manufacturer);
        entity.setSupplier(supplier);
        return entity;
    }

}

