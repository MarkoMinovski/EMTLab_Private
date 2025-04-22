package com.emt.springbackendapi.service.impl;

import com.emt.springbackendapi.model.domain.Book;
import com.emt.springbackendapi.model.domain.User;
import com.emt.springbackendapi.model.dto.LoginResponseDTO;
import com.emt.springbackendapi.model.dto.UpdateBookDTO;
import com.emt.springbackendapi.model.dto.UserDTO;
import com.emt.springbackendapi.model.enums.Role;
import com.emt.springbackendapi.model.exception.InvalidArgumentsException;
import com.emt.springbackendapi.model.exception.InvalidUserCredentialsException;
import com.emt.springbackendapi.model.exception.NoCopiesAvailableException;
import com.emt.springbackendapi.repository.UserRepository;
import com.emt.springbackendapi.security.JWTHelper;
import com.emt.springbackendapi.service.UserService;
import com.emt.springbackendapi.service.application.impl.BookApplicationServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookApplicationServiceImpl bookApplicationService;
    private final PasswordEncoder passwordEncoder;
    private final JWTHelper jwtHelper;

    public UserServiceImpl(UserRepository userRepository, BookApplicationServiceImpl bookApplicationService,
                           PasswordEncoder passwordEncoder, JWTHelper jwtHelper) {
        this.userRepository = userRepository;
        this.bookApplicationService = bookApplicationService;
        this.passwordEncoder = passwordEncoder;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public Optional<User> register(String username, String password,
                         String repeatPassword, Role role) {
        return Optional.of(userRepository.save(new User(username, passwordEncoder.encode(password),
                Objects.requireNonNullElse(role, Role.ROLE_USER))));
    }

    @Override
    public Optional<User> loginInternal(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty())
            throw new InvalidArgumentsException();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User doesn't exist"));
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new InvalidUserCredentialsException();
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<List<UpdateBookDTO>> checkoutWishlist(String username) throws NoCopiesAvailableException {
        Optional<User> user = userRepository.findByUsername(username);
        List<Book> wishlist = user.get().getWishlist();
        List<Book> booksToRemove = new ArrayList<>();
        List<UpdateBookDTO> pickedUpBooks = new ArrayList<>();

        for (Book b : wishlist) {
            try {
                bookApplicationService.borrow(b.getId());

                // if we get past this comment, we didn't throw an exception
                booksToRemove.add(b);
                pickedUpBooks.add(UpdateBookDTO.from(b));
            } catch (NoCopiesAvailableException ignored) {

            }

        }

        if (!pickedUpBooks.isEmpty()) {
            wishlist.removeAll(booksToRemove);
            userRepository.save(user.get());
            return Optional.of(pickedUpBooks);
        } else {
            return Optional.empty();
        }


    }

    @Override
    public Optional<User> findBasicUserInfoByUsername(String username) {
        return Optional.ofNullable(userRepository.findBasicInfoByUsername(username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).get();
    }

    public Optional<LoginResponseDTO> loginAndReturnJwt(UserDTO userDTO) {
        Optional<User> user = this.loginInternal(userDTO.username(), userDTO.password());

        if (user.isPresent()) {
            String token = jwtHelper.generateToken(user.get());

            return Optional.of(new LoginResponseDTO(token));
        }

        return Optional.empty();
    };

}
