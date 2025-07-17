package su25_se183660.carservice.pojos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Manufacturer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ManufacturerID")
    private int manufacturerId;

    @Column(name = "ManufacturerName", nullable = false)
    private String manufacturerName;

    @Column(name = "Description")
    @JsonIgnore
    private String description;

    @Column(name = "ManufacturerCountry")
    private String manufacturerCountry;

    @JsonIgnore
    @OneToMany(mappedBy = "manufacturer",cascade = CascadeType.ALL)
    List<CarInformation> carInformation;
}
