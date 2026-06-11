package org.henick.spendings.mapper;

import org.henick.spendings.dto.CategoryRequest;
import org.henick.spendings.dto.CategoryResponse;
import org.henick.spendings.model.Category;

public interface CategoryMapper {

    CategoryResponse mapToResponse(Category category);
    Category mapFromResponse(CategoryResponse response);

    Category mapFromRequest(CategoryRequest request);

}
