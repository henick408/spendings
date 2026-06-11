package org.henick.spendings.service;

import org.henick.spendings.dto.CategoryRequest;
import org.henick.spendings.dto.CategoryResponse;
import org.henick.spendings.mapper.CategoryMapper;
import org.henick.spendings.model.Category;
import org.henick.spendings.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryResponse> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryMapper::mapToResponse).toList();
    }

    @Override
    public CategoryResponse getById(Long id) {
        Category category =  categoryRepository.findById(id).orElse(null);
        return categoryMapper.mapToResponse(category);
    }

    @Override
    public CategoryResponse getByNameIgnoreCase(String name) {
        Category category = categoryRepository.findCategoryByNameIgnoreCase(name);
        return categoryMapper.mapToResponse(category);
    }

    @Override
    public CategoryResponse create(CategoryRequest categoryRequest) {
        Category category = categoryMapper.mapFromRequest(categoryRequest);
        Category createdCategory = categoryRepository.save(category);
        return categoryMapper.mapToResponse(createdCategory);
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest categoryRequest) {
        Category category = categoryMapper.mapFromRequest(categoryRequest);
        category.setId(id);
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.mapToResponse(updatedCategory);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return categoryRepository.existsByNameIgnoreCase(name);
    }

}
