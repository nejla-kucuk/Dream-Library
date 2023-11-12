package com.project.service.utils;

import com.project.entity.business.Author;
import com.project.entity.business.Book;
import com.project.entity.business.Category;
import com.project.entity.business.Publisher;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.message.ErrorMessages;
import com.project.repository.business.AuthorRepository;
import com.project.repository.business.BookRepository;
import com.project.repository.business.CategoryRepository;
import com.project.repository.business.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BusinessUtil {

    private  final BookRepository bookRepository;

    private final PublisherRepository publisherRepository;

    private final CategoryRepository categoryRepository;

    private final AuthorRepository authorRepository;


    // For use Books Endpoints
    public Book isBooksExistById(Long bookId){

        return bookRepository.findById(bookId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_BOOKS_MESSAGE,bookId)));
    }

    // For use Publisher Endpoints
    public Publisher isPublisherExistById(Long publisherId){

        return publisherRepository.findById(publisherId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_PUBLISHER_MESSAGE,publisherId)));

    }

    // For use Category Endpoints
    public Category isCategoryExistById(Long categoryId){

        return categoryRepository.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_CATEGORY_MESSAGE,categoryId)));

    }

    // For use Author Endpoints
    public Author isAuthorExistById(Long authorId){

        return authorRepository.findById(authorId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_AUTHOR_MESSAGE,authorId)));

    }




    // Helper Method For Publisher
    public List<Book> getBooksByPublisherId(Long publisherId) {

        return bookRepository.findByPublisherId(publisherId);

    }

    // Helper Method For Category
    public List<Book> getBooksByCategoryId(Long categoryId) {

        return bookRepository.findByCategoryId(categoryId);

    }

}
