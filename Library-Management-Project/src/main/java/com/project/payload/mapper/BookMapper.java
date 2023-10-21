package com.project.payload.mapper;

import com.project.entity.business.Book;
import com.project.payload.response.business.BookResponse;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    //  Pojo --> Response DTO
    public BookResponse mapBookToBookResponse(Book book){

        return BookResponse.builder()
                .id(book.getId())
                .bookName(book.getBookName())
                .isbn(book.getIsbn())
                .publishDate(book.getPublishDate())
                .pageCount(book.getPageCount())
                .active(book.isActive())
                .build();
    }


}
