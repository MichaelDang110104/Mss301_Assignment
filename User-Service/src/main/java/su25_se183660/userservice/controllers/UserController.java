package su25_se183660.userservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import su25_se183660.userservice.commons.ResponseHandler;
import su25_se183660.userservice.dtos.UserDTO;
import su25_se183660.userservice.services.IUserService;

import java.util.List;

@RestController
@RequestMapping( "/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final IUserService iUserService;


    @PostMapping("/register-account")
    public ResponseEntity<Object> createUser(UserDTO userDTO) {
        iUserService.createUser(userDTO);
        return ResponseHandler.responseBuilder("Create user successfully !", HttpStatus.OK, null);
    }

    @PutMapping("/update-user/{userID}")
    public ResponseEntity<Object> updateUser(UserDTO userDTO, @PathVariable int userID) {
        iUserService.updateUser(userDTO, userID);
        return ResponseHandler.responseBuilder("Update user successfully !", HttpStatus.OK, null);
    }

    @DeleteMapping("/delete-user/{userID}")
    public ResponseEntity<Object> deleteUser(@PathVariable int userID) {
        iUserService.deleteUser(userID);
        return ResponseHandler.responseBuilder("Delete user successfully !", HttpStatus.OK, null);
    }

    @GetMapping("/get-user/{userID}")
    public ResponseEntity<Object> getUser(@PathVariable int userID) {
        UserDTO userDTO = iUserService.getUser(userID);
        return ResponseHandler.responseBuilder("Get user successfully !", HttpStatus.OK, userDTO);
    }

    @GetMapping("/list-users")
    public ResponseEntity<Object> getUsers() {
        List<UserDTO> responseUsers = iUserService.getAllUsers();
        return ResponseHandler.responseBuilder("Get users successfully !", HttpStatus.OK, responseUsers);
    }

}
