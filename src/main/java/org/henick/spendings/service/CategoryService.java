package org.henick.spendings.service;

import org.henick.spendings.dto.CategoryRequest;
import org.henick.spendings.dto.CategoryResponse;
import org.henick.spendings.model.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getAll();
    CategoryResponse getById(Long id);
    CategoryResponse getByNameIgnoreCase(String name);
    CategoryResponse create(CategoryRequest categoryRequest);
    CategoryResponse update(Long id, CategoryRequest categoryRequest);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByNameIgnoreCase(String name);

}
