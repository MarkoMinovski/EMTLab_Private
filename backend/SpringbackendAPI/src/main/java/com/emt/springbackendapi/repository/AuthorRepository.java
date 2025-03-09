package com.emt.springbackendapi.repository;

import com.emt.springbackendapi.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
