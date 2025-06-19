package su25_se183660.userservice.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
