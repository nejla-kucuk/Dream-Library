package com.project.payload.mapper;

import com.project.entity.business.Book;
import com.project.payload.request.business.BookRequest;
import com.project.payload.response.business.BookResponse;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    //  POJO --> Response DTO
    public BookResponse mapBookToBookResponse(Book book){

        return BookResponse.builder()
                .id(book.getId())
                .bookName(book.getBookName())
                .isbn(book.getIsbn())
                .publishDate(book.getPublishDate())
                .pageCount(book.getPageCount())
                .active(book.isActive())
                .category(book.getCategory())
                .publisher(book.getPublisher())
                .author(book.getAuthor())
                .build();
    }


    // bookRequest DTO --> POJO
    public Book mapBookRequestToBook(BookRequest bookRequest) {

        return Book.builder()
                .bookName(bookRequest.getBookName())
                .isbn(bookRequest.getIsbn())
                .pageCount(bookRequest.getPageCount())
                .publishDate(bookRequest.getPublishDate())
                .image(bookRequest.getImage())
                .shelfCode(bookRequest.getShelfCode())
                .featured(bookRequest.isFeatured())
                .build();
    }

    // bookRequest DTO --> POJO
    public Book mapBookRequestToUpdatedBook(Long bookId, BookRequest bookRequest) {

        return mapBookRequestToBook(bookRequest)
                .toBuilder()
                .id(bookId)
                .build();
    }
}
