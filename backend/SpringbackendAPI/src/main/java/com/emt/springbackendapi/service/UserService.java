package com.emt.springbackendapi.service;

import com.emt.springbackendapi.model.enums.Role;
import com.emt.springbackendapi.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> register(String username, String password, String repeatPassword, Role role);

    Optional<User> login(String username, String password);

    Optional<User> findByUsername(String username);
}
