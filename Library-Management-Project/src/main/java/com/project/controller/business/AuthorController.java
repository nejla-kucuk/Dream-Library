package com.project.controller.business;

import com.project.payload.response.business.AuthorResponse;
import com.project.service.business.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


}
