package com.emt.springbackendapi.service.impl;

import com.emt.springbackendapi.model.User;
import com.emt.springbackendapi.model.enums.Role;
import com.emt.springbackendapi.repository.UserRepository;
import com.emt.springbackendapi.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> register(String username, String password,
                         String repeatPassword, Role role) {

        if (role == null) {
            return Optional.of(userRepository.save(new User(username, passwordEncoder.encode(password))));
        }

        return Optional.of(userRepository.save(new User(username, passwordEncoder.encode(password), role)));
    }

    @Override
    public Optional<User> login(String username, String password) {
        return Optional.ofNullable(userRepository.findByUsernameAndPassword(username, password));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // ??? it's the same thing
        return userRepository.findByUsername(username);
    }
}
