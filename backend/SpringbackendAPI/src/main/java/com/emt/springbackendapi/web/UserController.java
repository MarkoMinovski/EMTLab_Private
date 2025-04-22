package com.emt.springbackendapi.web;

import com.emt.springbackendapi.model.domain.User;
import com.emt.springbackendapi.model.dto.LoginResponseDTO;
import com.emt.springbackendapi.model.dto.UserDTO;
import com.emt.springbackendapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller", description = "Endpoints for user authentication, registration and info")
public class UserController {

    private final UserService userApplicationService;

    public UserController(UserService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @Operation(summary = "Register a new user", description = "Creates a new user account")
    @ApiResponses(
            value = {@ApiResponse(
                    responseCode = "200",
                    description = "User registered successfully"
            ), @ApiResponse(
                    responseCode = "400", description = "Invalid input or passwords do not match"
            )}
    )
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDTO userDTO) {
        return this.userApplicationService
                .register(userDTO.username(), userDTO.password(), userDTO.password(), null).map(ResponseEntity::ok)
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }

    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT")
    @ApiResponses(
            value = {@ApiResponse(
                    responseCode = "200",
                    description = "User authenticated successfully"
            ), @ApiResponse(responseCode = "404", description = "Invalid username or password")}
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        Optional<LoginResponseDTO> user = this.userApplicationService.loginAndReturnJwt(userDTO);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Unspecified Internal Server Error", "status", 500));
    }


    /*
    @Operation(summary = "User logout", description = "Ends the user's session")
    @ApiResponse(responseCode = "200", description = "User logged out successfully")
    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }
    */

    @Operation(summary = "Load user info", description = "Loads basic info for a user in the system")
    @ApiResponse(responseCode = "200", description = "Info returned successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "401", description = "Client sent bad request")
    @GetMapping("/get-basic-info")
    public ResponseEntity<User> loadUserInfo(String username) {
        Optional<User> userInfo = this.userApplicationService.findBasicUserInfoByUsername(username);

        if (userInfo.isPresent()) {
            return userInfo.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }

        return ResponseEntity.badRequest().build();
    }

}
