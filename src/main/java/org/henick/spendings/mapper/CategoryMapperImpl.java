package org.henick.spendings.mapper;

import org.henick.spendings.dto.CategoryRequest;
import org.henick.spendings.dto.CategoryResponse;
import org.henick.spendings.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements CategoryMapper {
    @Override
    public CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }

    @Override
    public Category mapFromResponse(CategoryResponse response) {
        return new Category(
                response.getId(),
                response.getName()
        );
    }

    @Override
    public Category mapFromRequest(CategoryRequest request) {
        return new Category(request.getName());
    }

}
