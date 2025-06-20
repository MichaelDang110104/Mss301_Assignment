package su25_se183660.car_rental_service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "Rental Service API", version = "1.0"),
		servers = {
				@Server(url = "http://localhost:8765/rental-service", description = "API Gateway")
		}
)
public class CarRentalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarRentalServiceApplication.class, args);
	}

}
