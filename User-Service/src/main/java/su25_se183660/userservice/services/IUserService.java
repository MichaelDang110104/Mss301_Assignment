package su25_se183660.userservice.services;

import su25_se183660.userservice.dtos.UserDTO;
import su25_se183660.userservice.pojos.User;

import java.util.List;

public interface IUserService {
    void createUser(UserDTO user);

    void updateUser(UserDTO user, int userID);

    void deleteUser(int userID);

    UserDTO getUser(int userID);
    List<UserDTO> getAllUsers();

    User getUserByEmail(String email);
}
