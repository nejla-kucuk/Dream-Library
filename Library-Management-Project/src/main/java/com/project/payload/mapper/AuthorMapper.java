package com.project.payload.mapper;

import com.project.entity.business.Author;
import com.project.payload.request.business.AuthorRequest;
import com.project.payload.response.business.AuthorResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    // POJO --> DTO Response
    public AuthorResponse mapAuthorToAuthorResponse(Author author){

        return AuthorResponse.builder()
                .id(author.getId())
                .authorName(author.getAuthorName())
                .builtIn(author.getBuiltIn())
                .build();
    }

    // DTO Request ---> POJO
    public Author mapAuthorRequestToAuthor(AuthorRequest authorRequest) {

        return Author.builder()
                .authorName(authorRequest.getAuthorName())
                .builtIn(authorRequest.getBuiltIn())
                .build();
    }

    // DTO Request ---> POJO
    public Author mapAuthorRequestToUpdatedAuthor(Long authorId, AuthorRequest authorRequest) {

        return mapAuthorRequestToAuthor(authorRequest)
                .toBuilder()
                .id(authorId)
                .build();
    }
}
