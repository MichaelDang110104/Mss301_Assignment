package su25_se183660.userservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;
import su25_se183660.userservice.commons.JWTUtils;
import su25_se183660.userservice.commons.ResponseHandler;
import su25_se183660.userservice.dtos.AuthResponse;
import su25_se183660.userservice.dtos.RequestAuthentication;
import su25_se183660.userservice.dtos.UserDTO;
import su25_se183660.userservice.pojos.User;
import su25_se183660.userservice.services.IUserService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final IUserService iUserService;

    private final JwtDecoder jwtDecoder;

    private final JWTUtils jwtUtils;

    @PostMapping("/register-account")
    public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO) {
        iUserService.createUser(userDTO);
        return ResponseHandler.responseBuilder("Create user successfully !", HttpStatus.OK, null);
    }

    @PutMapping("/update-user/{userID}")
    public ResponseEntity<Object> updateUser(@RequestBody UserDTO userDTO, @PathVariable int userID) {
        iUserService.updateUser(userDTO, userID);
        return ResponseHandler.responseBuilder("Update user successfully !", HttpStatus.OK, null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-user/{userD}")
    public ResponseEntity<Object> deleteUser(@PathVariable int userID) {
        iUserService.deleteUser(userID);
        return ResponseHandler.responseBuilder("Delete user successfully !", HttpStatus.OK, null);
    }

    @GetMapping("/get-user/{userID}")
    public ResponseEntity<Object> getUser(@PathVariable int userID) {
        UserDTO userDTO = iUserService.getUser(userID);
        return ResponseHandler.responseBuilder("Get user successfully !", HttpStatus.OK, userDTO);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/list-users")
    public ResponseEntity<Object> getUsers(
            @RequestHeader("X-User-Email") String email,
            @RequestHeader("X-User-Role") String role
    ) {
        System.out.println("Email: " + email);
        System.out.println("Role: " + role);
        List<UserDTO> responseUsers = iUserService.getAllUsers();
        return ResponseHandler.responseBuilder("Get users successfully !", HttpStatus.OK, responseUsers);
    }

    @GetMapping("/authen-jwt")
    public ResponseEntity<Object> getAuthenJwt(@RequestParam String token) {
        Jwt jwt = jwtDecoder.decode(token);
        String email = jwt.getSubject();
        String role = jwt.getClaim("role");
        if (iUserService.getUserByEmail(email) == null) {
            return ResponseHandler.responseBuilder("User does not exist !", HttpStatus.BAD_REQUEST, null);
        }
        HashMap<String, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("role", role);
        return ResponseHandler.responseBuilder("Authen jwt successfully !", HttpStatus.OK, response);
    }

    @PostMapping("/login")
    @Operation(description = "STAFF: alice.johnson@example.com  ||  USER: charlie.brown@example.com", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "ROLE: MENTOR", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Login Example", value = """
            {
              "email": "bob.smith@example.com",
              "password": "123456"
            }
            """))))
    public ResponseEntity<Object> login(@RequestBody RequestAuthentication authentication) {
        try {
            User user = iUserService.getUserByEmail(authentication.getEmail());
            boolean isMatch = new BCryptPasswordEncoder().matches(authentication.getPassword(), user.getPassword());
            if (!isMatch) {
                return ResponseHandler.responseBuilder("User not authenticated !", HttpStatus.UNAUTHORIZED, null);
            }
            if (user.isCustomerStatus() == false) {
                throw new Exception("User is not active");
            }
            String token = jwtUtils.createJwtToken(user);
            var response = AuthResponse.builder().token(token).email(user.getEmail()).role(user.getRole()).build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
