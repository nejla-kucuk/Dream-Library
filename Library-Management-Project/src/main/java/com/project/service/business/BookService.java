package com.project.service.business;

import com.project.entity.business.Book;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mapper.BookMapper;
import com.project.payload.message.ErrorMessages;
import com.project.payload.message.SuccessMessages;
import com.project.payload.request.business.BookRequest;
import com.project.payload.response.business.BookResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.repository.business.BookRepository;
import com.project.service.helper.PageableHelper;
import com.project.service.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final PageableHelper pageableHelper;

    private final Util util;

    private final BookMapper bookMapper;

    // getBooks()**** ??
    /*
    public Page<List<BookResponse>> getAllBooks(BookRequest bookRequest,
                                                int page,
                                                int size,
                                                String sort,
                                                String type) {


        //TODO: Bu kısmı tamamla.
    }
    */

    // getBooksById()*****
    public ResponseMessage<BookResponse> getBooksById(Long bookId) {

       Book book =  util.isBooksExistById(bookId);

       BookResponse bookResponse = bookMapper.mapBookToBookResponse(book);

        return ResponseMessage.<BookResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(bookResponse)
                .build();
    }

    // createBook()****
    public ResponseMessage<BookResponse> createBook(BookRequest bookRequest) {

        Book book = bookMapper.mapBookRequestToBook(bookRequest);

        //TODO: authorId, publisherId, categoryId'i setle.

        book.setActive(Boolean.TRUE);

        book.setBuiltIn(Boolean.FALSE);

        book.setCreateDate(LocalDateTime.now());

        book.setLoanable(Boolean.TRUE);

        Book savedBook = bookRepository.save(book);

        BookResponse bookResponse = bookMapper.mapBookToBookResponse(savedBook);

        return ResponseMessage.<BookResponse>builder()
                .message(SuccessMessages.BOOK_SAVED_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .object(bookResponse)
                .build();

    }

    // updateBook()***
    public ResponseMessage<BookResponse> updateBookBookById(Long bookId,
                                                            BookRequest bookRequest) {

        // BookId kontrolü
        util.isBooksExistById(bookId);

        // Request DTO --> POJO dönüşümü
        Book book = bookMapper.mapBookRequestToBook(bookRequest);

        //TODO: authorId, publisherId, categoryId'i response gönder.

        // Update edilen bilgileri DB'ye kaydet.
        Book savedBook = bookRepository.save(book);

        // POJO --> Response DTO dönüşümü
        BookResponse bookResponse = bookMapper.mapBookToBookResponse(savedBook);

        return ResponseMessage.<BookResponse>builder()
                .message(SuccessMessages.BOOK_UPDATE_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .object(bookResponse)
                .build();

    }

    // deleteBook()***
    public ResponseMessage<BookResponse> deleteBookById(Long bookId) {

        //bookId kontorülü
        Book book = util.isBooksExistById(bookId);

        //TODO: Eğer kitap ödünç alınacaklar listesinde ise hata mesajı gönder. Değilse sil.

        bookRepository.delete(book);

        BookResponse deletedBook = bookMapper.mapBookToBookResponse(book);

        return ResponseMessage.<BookResponse>builder()
                .message(SuccessMessages.BOOK_DELETE_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .object(deletedBook)
                .build();
    }


}
