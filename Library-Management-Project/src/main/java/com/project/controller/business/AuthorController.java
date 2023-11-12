package com.project.controller.business;

import com.project.payload.request.business.AuthorRequest;
import com.project.payload.response.business.AuthorResponse;
import com.project.payload.response.business.PublisherResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.service.business.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    // getAllAuthor()******
    // http://localhost:8080//authors?page=1&size=10&sort=name&type=asc
    @GetMapping("/authors")
    @PreAuthorize("!(hasAuthority('ADMIN') and hasAuthority('EMPLOYEE') and hasAuthority('MEMBER'))")
    public Page<List<AuthorResponse>> getAllAuthor(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "20") int size,
                                                   @RequestParam(value = "sort", defaultValue = "authorName") String sort,
                                                   @RequestParam(value = "type", defaultValue = "asc") String type){

        return authorService.getAllAuthor(page,size,sort,type);

    }

    // getAuthorById()******
    // http://localhost:8080/authors/4
    @GetMapping("/authors/{authorId}")
    @PreAuthorize("!(hasAuthority('ADMIN') and hasAuthority('EMPLOYEE') and hasAuthority('MEMBER'))")
    public ResponseMessage<AuthorResponse> getAuthorById(@PathVariable Long authorId){

        return authorService.getAuthorById(authorId);

    }

    // createAuthor()********
    // http://localhost:8080/authors
    @PostMapping("/authors")
    @PreAuthorize("(hasAuthority('ADMIN'))")
    public ResponseMessage<AuthorResponse> createAuthor(@RequestBody @Valid AuthorRequest authorRequest){

        return authorService.createAuthor(authorRequest);
    }


    // updateAuthorById()********
    // http://localhost:8080/authors/4
    @PutMapping("/authors/{authorId}")
    @PreAuthorize("(hasAuthority('ADMIN'))")
    public ResponseMessage<AuthorResponse> updateAuthorById(@RequestParam Long authorId,
                                                            @RequestBody @Valid AuthorRequest authorRequest)
    {
        return authorService.updateAuthorById(authorId, authorRequest);
    }




}
