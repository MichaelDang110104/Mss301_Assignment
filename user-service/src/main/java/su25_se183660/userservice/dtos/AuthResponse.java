package su25_se183660.userservice.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import su25_se183660.userservice.enums.Role;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse {
    String email;
    String token;
    Role role;
}
