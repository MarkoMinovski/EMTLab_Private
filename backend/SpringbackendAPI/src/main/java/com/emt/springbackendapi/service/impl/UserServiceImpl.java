package com.emt.springbackendapi.service.impl;

import com.emt.springbackendapi.model.domain.Book;
import com.emt.springbackendapi.model.domain.User;
import com.emt.springbackendapi.model.dto.UpdateBookDTO;
import com.emt.springbackendapi.model.enums.Role;
import com.emt.springbackendapi.model.exception.NoCopiesAvailableException;
import com.emt.springbackendapi.repository.UserRepository;
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

    public UserServiceImpl(UserRepository userRepository, BookApplicationServiceImpl bookApplicationService,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.bookApplicationService = bookApplicationService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> register(String username, String password,
                         String repeatPassword, Role role) {
        return Optional.of(userRepository.save(new User(username, passwordEncoder.encode(password),
                Objects.requireNonNullElse(role, Role.ROLE_USER))));
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
    public Optional<List<UpdateBookDTO>> checkoutWishlist(String username) throws NoCopiesAvailableException {
        User user = userRepository.findByUsername(username);
        List<Book> wishlist = user.getWishlist();
        List<Book> booksToRemove = new ArrayList<>();
        List<UpdateBookDTO> pickedUpBooks = new ArrayList<>();

        for (Book b : wishlist) {
            try {
                bookApplicationService.borrow(b.getId());

                // if we get past this comment, we didn't throw an exception
                booksToRemove.add(b);
                pickedUpBooks.add(UpdateBookDTO.from(b));
            } catch (NoCopiesAvailableException _) {

            }

        }

        if (!pickedUpBooks.isEmpty()) {
            wishlist.removeAll(booksToRemove);
            userRepository.save(user);
            return Optional.of(pickedUpBooks);
        } else {
            return Optional.empty();
        }


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // ??? it's the same thing
        return userRepository.findByUsername(username);
    }


}
