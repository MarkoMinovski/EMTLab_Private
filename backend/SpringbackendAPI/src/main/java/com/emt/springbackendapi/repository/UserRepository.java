package com.emt.springbackendapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.emt.springbackendapi.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);
}
