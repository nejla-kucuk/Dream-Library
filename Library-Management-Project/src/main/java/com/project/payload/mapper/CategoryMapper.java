package com.project.payload.mapper;

import com.project.entity.business.Category;
import com.project.payload.response.business.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    // POJO --> Response DTO
    public CategoryResponse mapCategoryToCategoryResponse(Category category){

        return CategoryResponse.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .builtIn(category.isBuiltIn())
                .sequence(category.getSequence())
                .build();
    }
}
