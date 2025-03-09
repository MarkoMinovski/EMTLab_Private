package com.emt.springbackendapi.repository;

import com.emt.springbackendapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
