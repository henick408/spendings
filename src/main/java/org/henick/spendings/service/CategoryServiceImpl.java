package org.henick.spendings.service;

import org.henick.spendings.dto.CategoryRequest;
import org.henick.spendings.dto.CategoryResponse;
import org.henick.spendings.mapper.CategoryMapper;
import org.henick.spendings.model.Category;
import org.henick.spendings.model.User;
import org.henick.spendings.model.UserRole;
import org.henick.spendings.repository.CategoryRepository;
import org.henick.spendings.security.AuthUser;
import org.henick.spendings.security.CurrentUserProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CurrentUserProvider currentUserProvider;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper, CurrentUserProvider currentUserProvider) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        AuthUser authUser = currentUserProvider.getCurrentUser();
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> categoryResponses = categories.stream().map(categoryMapper::mapToResponse).toList();

        if (authUser.getRole() == UserRole.EMPLOYEE) {
            return categoryResponses;
        }
        return categoryResponses.stream()
                .filter(response -> response.getUserId().equals(authUser.getId()))
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category =  categoryRepository.findById(id).orElse(null);
        return categoryMapper.mapToResponse(category);
    }

    @Override
    public CategoryResponse getCategoryByNameIgnoreCase(String name) {
        Category category = categoryRepository.findCategoryByNameIgnoreCase(name);
        return categoryMapper.mapToResponse(category);
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        AuthUser authUser = currentUserProvider.getCurrentUser();
        Category category = categoryMapper.mapFromRequest(categoryRequest);
        Category createdCategory;
        if (authUser.getRole() == UserRole.EMPLOYEE) {
            createdCategory = categoryRepository.save(category);
            return categoryMapper.mapToResponse(createdCategory);
        }
        User user = new User(authUser.getId());
        category.setUser(user);
        createdCategory = categoryRepository.save(category);
        return categoryMapper.mapToResponse(createdCategory);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryMapper.mapFromRequest(categoryRequest);
        category.setId(id);
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.mapToResponse(updatedCategory);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public boolean existsCategoryById(Long id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public boolean existsCategoryByNameIgnoreCase(String name) {
        return categoryRepository.existsByNameIgnoreCase(name);
    }

}
