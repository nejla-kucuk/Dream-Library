package com.project.service.utils;

import com.project.entity.business.Book;
import com.project.entity.business.Category;
import com.project.entity.business.Publisher;
import com.project.entity.user.User;
import com.project.exception.BadRequestException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.message.ErrorMessages;
import com.project.payload.request.business.BookRequest;
import com.project.repository.business.BookRepository;
import com.project.repository.business.CategoryRepository;
import com.project.repository.business.PublisherRepository;
import com.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Util{

    private final UserRepository userRepository;

    private  final BookRepository bookRepository;

    private final PublisherRepository publisherRepository;

    private final CategoryRepository categoryRepository;

    public User isUserExist(Long userId){

        return userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, userId)));
    }

    public boolean isBuiltIn(User user){

        if(Boolean.TRUE.equals(user.isBuiltIn())) {
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
        }

        return false;

    }

    public User getAttributeUser(HttpServletRequest request){

        String email = (String) request.getAttribute("email");

        return userRepository.findByEmailEquals(email);
    }


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


    // Helper Method For Publisher
    public List<Book> getBooksByPublisherId(Long publisherId) {

        return bookRepository.findByPublisherId(publisherId);

    }

    // Helper Method For Category
    public List<Book> getBooksByCategoryId(Long categoryId) {

        return bookRepository.findByCategoryId(categoryId);

    }






}
