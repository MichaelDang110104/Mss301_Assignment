package su25_se183660.carservice.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import su25_se183660.carservice.dtos.CarInformationDTO;
import su25_se183660.carservice.dtos.CarInformationResponse;
import su25_se183660.carservice.pojos.CarInformation;
import su25_se183660.carservice.pojos.Manufacturer;
import su25_se183660.carservice.pojos.Supplier;
import su25_se183660.carservice.repositories.ICarInformationRepo;
import su25_se183660.carservice.repositories.IManufacturerRepo;
import su25_se183660.carservice.repositories.ISupplierRepo;
import su25_se183660.carservice.utils.CarInformationMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarInformationService implements ICarInformationService {

    private final ICarInformationRepo carInformationRepository;
    private final IManufacturerRepo manufacturerRepository;
    private final ISupplierRepo supplierRepository;

    @Override
    public List<CarInformationResponse> getAllCars() {
        return carInformationRepository.findAll()
                .stream()
                .map(CarInformationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CarInformationResponse getCarById(int carId) {
        CarInformation car = carInformationRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + carId));
        return CarInformationMapper.toResponse(car);
    }

    @Override
    public CarInformationDTO createCar(CarInformationDTO carDTO) {
        Manufacturer manufacturer = manufacturerRepository.findById(carDTO.getManufacturerId())
                .orElseThrow(() -> new EntityNotFoundException("Manufacturer not found"));
        Supplier supplier = supplierRepository.findById(carDTO.getSupplierId())
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found"));

        CarInformation savedCar = carInformationRepository.save(
                CarInformationMapper.toEntity(carDTO, manufacturer, supplier)
        );

        return CarInformationMapper.toDto(savedCar);
    }

    @Override
    public CarInformationDTO updateCar(int carId, CarInformationDTO carDTO) {
        CarInformation existingCar = carInformationRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + carId));

        Manufacturer manufacturer = manufacturerRepository.findById(carDTO.getManufacturerId())
                .orElseThrow(() -> new EntityNotFoundException("Manufacturer not found"));
        Supplier supplier = supplierRepository.findById(carDTO.getSupplierId())
                .orElseThrow(() -> new EntityNotFoundException("Supplier not found"));

        CarInformation updatedCar = CarInformationMapper.updateEntity(existingCar, carDTO, manufacturer, supplier);
        updatedCar.setCarId(existingCar.getCarId());
        return CarInformationMapper.toDto(carInformationRepository.save(updatedCar));
    }

    @Override
    public void deleteCar(int carId) {
        carInformationRepository.deleteById(carId);
    }
}

