package su25_se183660.car_rental_service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import su25_se183660.car_rental_service.dtos.RentingTransactionDTO;
import su25_se183660.car_rental_service.services.IRentingService;
import su25_se183660.car_rental_service.utils.ResponseHandler;

@RestController
@RequestMapping("/api/rentings")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RentingController {

    private final IRentingService rentingService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/create-transaction")
    public ResponseEntity<Object> createTransaction(@RequestBody RentingTransactionDTO dto) {
        RentingTransactionDTO created = rentingService.createTransaction(dto);
        return ResponseHandler.responseBuilder("Create transaction", HttpStatus.CREATED, created);
    }

    @GetMapping("/get-all-transaction/{customerid}")
    public ResponseEntity<Object> getAllTransactions(@PathVariable int customerid, @RequestHeader("X-User-Email") String email) {
        return ResponseHandler.responseBuilder("Get all transactions", HttpStatus.OK, rentingService.getAllTransactions(customerid, email));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/get-report")
    public ResponseEntity<Object> getReport(@RequestParam String startDate, @RequestParam String endDate) {
        return ResponseHandler.responseBuilder("Get statistic successfully !", HttpStatus.OK, rentingService.getReportStatistics(startDate, endDate));
    }

    @GetMapping("/get-transaction/{id}")
    public ResponseEntity<RentingTransactionDTO> getTransactionById(@PathVariable Integer id) {
        return rentingService.getTransactionById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}

