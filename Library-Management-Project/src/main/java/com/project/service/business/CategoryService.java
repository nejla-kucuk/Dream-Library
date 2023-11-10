package com.project.service.business;

import com.project.payload.response.business.CategoryResponse;
import com.project.payload.response.business.PublisherResponse;
import com.project.repository.business.CategoryRepository;
import com.project.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final PageableHelper pageableHelper;

    private final CategoryMapper categoryMapper;

    //getAllCategories()*******
    public Page<List<CategoryResponse>> getAllCategories(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        List<CategoryResponse> categoryResponseList = categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapCategoryToCategoryResponse)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), categoryResponseList.size());

        List<CategoryResponse> content = categoryResponseList.subList(start, end);

        return new PageImpl<>(List.of(content), pageable, categoryResponseList.size());
    }

}
