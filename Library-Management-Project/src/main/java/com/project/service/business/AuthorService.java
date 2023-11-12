package com.project.service.business;

import com.project.entity.business.Author;
import com.project.payload.mapper.AuthorMapper;
import com.project.payload.request.business.AuthorRequest;
import com.project.payload.response.business.AuthorResponse;
import com.project.payload.response.business.PublisherResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.repository.business.AuthorRepository;
import com.project.service.helper.PageableHelper;
import com.project.service.utils.BusinessUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    private final PageableHelper pageableHelper;

    private final AuthorMapper authorMapper;

    private final BusinessUtil util;


    // getAllAuthor()******
    public Page<List<AuthorResponse>> getAllAuthor(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        List<AuthorResponse> authorResponseList = authorRepository.findAll()
                .stream()
                .map(authorMapper::mapAuthorToAuthorResponse)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), authorResponseList.size());

        List<AuthorResponse> content = authorResponseList.subList(start, end);

        return new PageImpl<>(List.of(content), pageable, authorResponseList.size());
    }


    // getAuthorById()******
    public ResponseMessage<AuthorResponse> getAuthorById(Long authorId) {

        Author author = util.isAuthorExistById(authorId);

        AuthorResponse response = authorMapper.mapAuthorToAuthorResponse(author);

        return ResponseMessage.<AuthorResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();

    }


    // createAuthor()********
    public ResponseMessage<AuthorResponse> createAuthor(AuthorRequest authorRequest) {

    }
}
