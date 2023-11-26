package com.project.service.business;

import com.project.entity.business.Book;
import com.project.entity.business.Category;
import com.project.exception.BadRequestException;
import com.project.payload.mapper.CategoryMapper;
import com.project.payload.message.ErrorMessages;
import com.project.payload.message.SuccessMessages;
import com.project.payload.request.business.CategoryRequest;
import com.project.payload.response.business.CategoryResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.repository.business.CategoryRepository;
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
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final PageableHelper pageableHelper;

    private final CategoryMapper categoryMapper;

    private final BusinessUtil util;


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

    // getCategoriesById()****
    public ResponseMessage<CategoryResponse> getCategoriesById(Long categoryId) {

        Category category = util.isCategoryExistById(categoryId);

        CategoryResponse response = categoryMapper.mapCategoryToCategoryResponse(category);

        return ResponseMessage.<CategoryResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();
    }

    // createCategory()******
    public ResponseMessage<CategoryResponse> createCategory(CategoryRequest categoryRequest) {

        Category category = categoryMapper.mapCategoryRequestToCategory(categoryRequest);

        Category savedCategory = categoryRepository.save(category);

        CategoryResponse response = categoryMapper.mapCategoryToCategoryResponse(savedCategory);

        return ResponseMessage.<CategoryResponse>builder()
                .message(SuccessMessages.CATEGORY_SAVED_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();
    }

    // updateCategoryById()******
    public ResponseMessage<CategoryResponse> updateCategoryById(Long categoryId,
                                                                CategoryRequest categoryRequest) {

        util.isCategoryExistById(categoryId);

        Category category = categoryMapper.mapCategoryRequestToUpdatedCategory(categoryId,categoryRequest);

        Category updatedCategory = categoryRepository.save(category);

        CategoryResponse response = categoryMapper.mapCategoryToCategoryResponse(updatedCategory);

        return ResponseMessage.<CategoryResponse>builder()
                .message(SuccessMessages.CATEGORY_UPDATE_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();
    }

    public ResponseMessage<CategoryResponse> deleteCategoryById(Long categoryId) {

        Category category = util.isCategoryExistById(categoryId);

        // Category'e ait kitabın olup olamadığı kontrol edilir.
        List<Book> books = util.getBooksByCategoryId(categoryId);

        if(!books.isEmpty()){
            throw new BadRequestException(ErrorMessages.NOT_DELETED_CATEGORY_BY_ID);
        }

        CategoryResponse response = categoryMapper.mapCategoryToCategoryResponse(category);

        categoryRepository.delete(category);

        return ResponseMessage.<CategoryResponse>builder()
                .message(SuccessMessages.CATEGORY_DELETE_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();

    }


}
