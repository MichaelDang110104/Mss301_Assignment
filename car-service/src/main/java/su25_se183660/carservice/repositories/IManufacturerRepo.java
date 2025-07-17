package su25_se183660.carservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import su25_se183660.carservice.pojos.Manufacturer;

public interface IManufacturerRepo extends JpaRepository<Manufacturer, Integer> {
}
