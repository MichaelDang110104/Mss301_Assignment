package su25_se183660.userservice.services.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import su25_se183660.userservice.dtos.UserDTO;
import su25_se183660.userservice.pojos.User;
import su25_se183660.userservice.repositories.IUserRepository;
import su25_se183660.userservice.services.IUserService;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements IUserService, UserDetailsService {

    private final IUserRepository iUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = iUserRepository.getUserByEmail(email);
        if (user != null) {
            var springUser = org.springframework.security.core.userdetails
                    .User.withUsername(user.getEmail()).password(user.getPassword()).roles(user.getRole().name()).build();
            return springUser;
        }
        return null;
    }

    @Override
    public void createUser(UserDTO user) {
        User newUser = new User();
        newUser = this.mapUserDTOToUser(user, newUser);
        newUser.setCustomerStatus(true);
        String password = user.getPassword();
        newUser.setPassword(new BCryptPasswordEncoder().encode(password));
        iUserRepository.save(newUser);
    }

    @Override
    public void updateUser(UserDTO userDTO, int userID) {
        User user = iUserRepository.getReferenceById(userID);
        user = this.mapUserDTOToUser(userDTO, user);
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        iUserRepository.save(user);
    }

    @Override
    public void deleteUser(int userID) {
        User user = iUserRepository.findById(userID).orElse(null);
        iUserRepository.delete(user);
    }

    @Override
    public UserDTO getUser(int userID) {
        UserDTO userDTO = mapUserDTOToUser(iUserRepository.getOne(userID));
        return userDTO;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = iUserRepository.findAll();
        System.out.println(users);
        return iUserRepository.findAll().stream().map(this::mapUserDTOToUser).toList();
    }

    @Override
    public User getUserByEmail(String email) {
        return iUserRepository.getUserByEmail(email);
    }

    public UserDTO mapUserDTOToUser(User user) {
        return new UserDTO().builder().customerName(user.getCustomerName()).password(user.getPassword()).email(user.getEmail()).phone(user.getPhone()).customerStatus(user.isCustomerStatus()).customerBirthday(user.getCustomerBirthday()).build();
    }

    public User mapUserDTOToUser(UserDTO userDTO, User user) {
        user.setCustomerName(userDTO.getCustomerName());
        user.setPassword(userDTO.getPassword()); // Raw for now
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setCustomerStatus(userDTO.isCustomerStatus());
        user.setCustomerBirthday(userDTO.getCustomerBirthday());
        user.setRole(userDTO.getRole());
        return user;
    }

}
