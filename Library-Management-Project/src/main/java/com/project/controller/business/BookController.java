package com.project.controller.business;

import com.project.payload.request.business.BookRequest;
import com.project.payload.response.business.BookResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.service.business.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // getBooks()**** ---for search books---
    // http://localhost:8080/books?q=sefiller&cat=4&author=34&publisher=42&page=1&size=10&sort=name&type=asc
    @GetMapping("books")
    public Page<List<BookResponse>> getAllBooks(
            @RequestBody @Valid BookRequest bookRequest,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "bookName") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type){

        return bookService.getAllBooks(bookRequest,page,size,sort,type);
    }

    // getBooksById()*****
    // http://localhost:8080/books/5
    @GetMapping("books/{bookId}")
    public ResponseMessage<BookResponse> getBooksById(@PathVariable Long bookId){

        return bookService.getBooksById(bookId);

    }

    // createBook()****
    // http://localhost:8080/books
    @PostMapping("books")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<BookResponse> createBook(@RequestBody @Valid BookRequest bookRequest){

        return bookService.createBook(bookRequest);
    }

    // updateBook()***
    // http://localhost:8080/books/5
    @PutMapping("books/{bookId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<BookResponse> updateBookBookById(@PathVariable Long bookId,
                                                            @RequestBody BookRequest bookRequest){

        return bookService.updateBookBookById(bookId,bookRequest);

    }

    // deleteBook()***
    // http://localhost:8080/books/5
    @DeleteMapping("books/{bookId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<BookResponse> deleteBookById(@PathVariable Long bookId){

        return bookService.deleteBookById(bookId);

    }





}
