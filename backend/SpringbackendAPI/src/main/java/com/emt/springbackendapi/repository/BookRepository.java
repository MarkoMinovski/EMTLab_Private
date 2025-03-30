package com.emt.springbackendapi.repository;

import com.emt.springbackendapi.model.domain.Book;
import com.emt.springbackendapi.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByAuthor_Id(Long authorId);

    List<Book> findBooksByCategory(Category category);

    List<Book> findBookByAvailableCopiesGreaterThan(int i);
}
