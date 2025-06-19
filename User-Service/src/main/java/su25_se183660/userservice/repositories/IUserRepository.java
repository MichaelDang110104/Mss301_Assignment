package su25_se183660.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import su25_se183660.userservice.pojos.User;

public interface IUserRepository extends JpaRepository<User, Integer> {
    User getUserByEmail(String email);
}
