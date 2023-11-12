package com.project.repository.business;

import com.project.entity.business.Book;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {
    
    List<Book> findByPublisherId(Long publisherId);

    List<Book> findByCategoryId(Long categoryId);

    List<Book> findByAuthorId(Long authorId);
}
