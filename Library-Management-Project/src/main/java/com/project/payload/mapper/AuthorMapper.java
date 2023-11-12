package com.project.payload.mapper;

import com.project.entity.business.Author;
import com.project.payload.response.business.AuthorResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    // POJO --> DTO Response
    public AuthorResponse mapAuthorToAuthorResponse(Author author){

        return AuthorResponse.builder()



    }
}
