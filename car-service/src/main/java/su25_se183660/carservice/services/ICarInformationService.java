package su25_se183660.carservice.services;

import su25_se183660.carservice.dtos.CarInformationDTO;
import su25_se183660.carservice.dtos.CarInformationResponse;

import java.util.List;

public interface ICarInformationService {
    List<CarInformationResponse> getAllCars();
    CarInformationResponse getCarById(int carId);
    CarInformationDTO createCar(CarInformationDTO carDTO);
    CarInformationDTO updateCar(int carId, CarInformationDTO carDTO);
    void deleteCar(int carId);
}
