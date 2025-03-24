package com.emt.springbackendapi.web;

import com.emt.springbackendapi.model.User;
import com.emt.springbackendapi.model.dto.UserDTO;
import com.emt.springbackendapi.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userApplicationService;

    public UserController(UserService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDTO userDTO) {
        return this.userApplicationService
                .register(userDTO.username(), userDTO.password(), userDTO.password(), null).map(ResponseEntity::ok)
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        Optional<User> user = this.userApplicationService.login(userDTO.username(), userDTO.password());

        if (user.isPresent()) {
            request.getSession().setAttribute("user", user.get());
            return ResponseEntity.ok(user.get());
        }

        return ResponseEntity.notFound().build();
    }


    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }


}
