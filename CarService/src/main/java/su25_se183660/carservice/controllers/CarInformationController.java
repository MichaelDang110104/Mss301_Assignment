package su25_se183660.carservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import su25_se183660.carservice.dtos.CarInformationDTO;
import su25_se183660.carservice.dtos.CarInformationResponse;
import su25_se183660.carservice.services.ICarInformationService;
import su25_se183660.carservice.utils.ResponseHandler;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarInformationController {

    private final ICarInformationService carInformationService;

    @GetMapping("/get-all-cars")
    public ResponseEntity<Object> getAllCars() {
        List<CarInformationResponse> cars = carInformationService.getAllCars();
        return ResponseHandler.responseBuilder("Get all cars", HttpStatus.OK, cars);
    }

    @GetMapping("/get-car/{id}")
    public ResponseEntity<Object> getCarById(@PathVariable int id) {
        return ResponseHandler.responseBuilder("Get car", HttpStatus.OK, carInformationService.getCarById(id));
    }

    @PostMapping("create-car")
    public ResponseEntity<Object> createCar(@Valid @RequestBody CarInformationDTO carDTO) {
        CarInformationDTO created = carInformationService.createCar(carDTO);
        return ResponseHandler.responseBuilder("Create car", HttpStatus.OK, created);
    }

    @PutMapping("/update-car/{id}")
    public ResponseEntity<Object> updateCar(
            @PathVariable int id,
            @Valid @RequestBody CarInformationDTO carDTO
    ) {
        CarInformationDTO updated = carInformationService.updateCar(id, carDTO);
        return ResponseHandler.responseBuilder("Update car", HttpStatus.OK, updated);
    }

    @DeleteMapping("/delete-car/{id}")
    public ResponseEntity<Object> deleteCar(@PathVariable int id) {
        carInformationService.deleteCar(id);
        return ResponseHandler.responseBuilder("Delete car", HttpStatus.OK, null);
    }
}

