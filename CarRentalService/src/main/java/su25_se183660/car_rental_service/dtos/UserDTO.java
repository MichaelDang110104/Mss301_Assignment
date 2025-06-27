package su25_se183660.car_rental_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import su25_se183660.car_rental_service.Role;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
    private String customerName;

    private String password;

    private String email;

    private String phone;

    private boolean customerStatus;

    private LocalDate customerBirthday;

    private Role role;
}
