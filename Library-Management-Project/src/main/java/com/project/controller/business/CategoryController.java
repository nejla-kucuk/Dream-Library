package com.project.controller.business;

import com.project.payload.response.business.CategoryResponse;
import com.project.service.business.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    //getAllCategories()*******
    // http://localhost:8080/categories?page=1&size=10&sort=name&type=asc
    @GetMapping("categories")
    @PreAuthorize("!(hasAuthority('ADMIN') and hasAuthority('EMPLOYEE') and hasAuthority('MEMBER'))")
    public Page<List<CategoryResponse>> getAllCategories(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "20") int size,
                                                         @RequestParam(value = "sort", defaultValue = "categoryName") String sort,
                                                         @RequestParam(value = "type", defaultValue = "asc") String type)
    {

        return categoryService.getAllCategories(page,size,sort,type);
    }
}
