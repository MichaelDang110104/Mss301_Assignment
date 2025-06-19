package su25_se183660.car_rental_service.controllers;

// RentingController.java

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import su25_se183660.car_rental_service.dtos.RentingTransactionDTO;
import su25_se183660.car_rental_service.services.IRentingService;
import su25_se183660.car_rental_service.utils.ResponseHandler;

import java.util.List;

@RestController
@RequestMapping("/api/rentings")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RentingController {

    private final IRentingService rentingService;

    @PostMapping("/create-transaction")
    public ResponseEntity<Object> createTransaction(@RequestBody RentingTransactionDTO dto) {
        RentingTransactionDTO created = rentingService.createTransaction(dto);
        return ResponseHandler.responseBuilder("Create transaction", HttpStatus.CREATED, created);
    }

    @GetMapping("/get-all-transaction/{customerid}")
    public ResponseEntity<Object> getAllTransactions(@PathVariable int customerid) {
        return ResponseHandler.responseBuilder("Get all transactions", HttpStatus.OK, rentingService.getAllTransactions(customerid));
    }

    @GetMapping("/get-transaction/{id}")
    public ResponseEntity<RentingTransactionDTO> getTransactionById(@PathVariable Integer id) {
        return rentingService.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

