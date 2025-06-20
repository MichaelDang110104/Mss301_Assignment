package su25_se183660.carservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "Car Service API", version = "1.0"),
		servers = {
				@Server(url = "http://localhost:8765/car-service", description = "API Gateway")
		}
)
public class CarserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarserviceApplication.class, args);
	}

}
