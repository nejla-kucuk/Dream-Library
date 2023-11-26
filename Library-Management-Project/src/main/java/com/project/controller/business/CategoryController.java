package com.project.controller.business;

import com.project.payload.request.business.CategoryRequest;
import com.project.payload.response.business.CategoryResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.service.business.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // getAllCategories()*******
    // http://localhost:8080/categories?page=1&size=10&sort=name&type=asc
    @GetMapping("categories")
    public Page<List<CategoryResponse>> getAllCategories(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "20") int size,
                                                         @RequestParam(value = "sort", defaultValue = "categoryName") String sort,
                                                         @RequestParam(value = "type", defaultValue = "asc") String type)
    {

        return categoryService.getAllCategories(page,size,sort,type);
    }

    // getCategoriesById()****
    // http://localhost:8080/categories/4
    @GetMapping("categories/{categoryId}")
    public ResponseMessage<CategoryResponse> getCategoriesById(@PathVariable Long categoryId){

        return categoryService.getCategoriesById(categoryId);
    }

    // createCategory()******
    // http://localhost:8080/categories
    @PostMapping("categories")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest categoryRequest){

        return categoryService.createCategory(categoryRequest);
    }

    // updateCategoryById()******
    // http://localhost:8080/categories/4
    @PutMapping("categories/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<CategoryResponse> updateCategoryById(@PathVariable Long categoryId,
                                                                @RequestBody @Valid CategoryRequest categoryRequest){

        return categoryService.updateCategoryById(categoryId,categoryRequest);
    }

    // deleteCategoryById()******
    // http://localhost:8080/categories/4
    @DeleteMapping("categories/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<CategoryResponse> deleteCategoryById(@PathVariable Long categoryId){

        return categoryService.deleteCategoryById(categoryId);
    }

}
