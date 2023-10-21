package com.project.service.business;

import com.project.entity.business.Book;
import com.project.payload.mapper.BookMapper;
import com.project.payload.message.SuccessMessages;
import com.project.payload.request.business.BookRequest;
import com.project.payload.response.business.BookResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.UserResponse;
import com.project.repository.business.BookRepository;
import com.project.service.helper.PageableHelper;
import com.project.service.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final PageableHelper pageableHelper;

    private final Util util;

    private final BookMapper bookMapper;

    // getBooks()**** ??
    public Page<List<BookResponse>> getBooks(BookRequest bookRequest,
                                             int page,
                                             int size,
                                             String sort,
                                             String type) {


        //TODO: Bu kısmı tamamla.
    }


    // getBooksById()*****
    public ResponseMessage<BookResponse> getBooksById(Long bookId) {

       Book book =  util.isBooksExistById(bookId);

       BookResponse bookResponse = bookMapper.mapBookToBookResponse(book);

        return ResponseMessage.<BookResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(bookResponse)
                .build();
    }
}
