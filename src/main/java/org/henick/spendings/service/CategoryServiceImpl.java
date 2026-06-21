package org.henick.spendings.service;

import org.henick.spendings.dto.CategoryRequest;
import org.henick.spendings.dto.CategoryResponse;
import org.henick.spendings.exception.CategoryAlreadyExistsException;
import org.henick.spendings.exception.NoSuchCategoryExistsException;
import org.henick.spendings.mapper.CategoryMapper;
import org.henick.spendings.model.Category;
import org.henick.spendings.model.User;
import org.henick.spendings.model.UserRole;
import org.henick.spendings.repository.CategoryRepository;
import org.henick.spendings.security.CurrentUserProvider;
import org.springframework.security.access.AccessDeniedException;
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
        List<Category> categories = categoryRepository.findAll();

        if (currentUserProvider.hasRole(UserRole.EMPLOYEE)) {
            return categories.stream()
                    .map(categoryMapper::mapToResponse)
                    .toList();
        }
        User user = new User(currentUserProvider.getCurrentUser());
        return categories.stream()
                .filter(response -> response.getUser() == null || response.getUser().equals(user))
                .map(categoryMapper::mapToResponse)
                .toList();
    }
    // user nie ma dostępu do nie swoich kategorii -- check
    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category =  categoryRepository.findById(id)
                // 404
                .orElseThrow(() -> new NoSuchCategoryExistsException("No such category exists"));
        if (!currentUserProvider.hasRole(UserRole.EMPLOYEE)) {
            if (category.getUser() != null && currentUserProvider.isCurrentUser(category.getUser())) {
                // 403
                throw new AccessDeniedException("Unauthorized access to category");
            }
        }
        return categoryMapper.mapToResponse(category);
    }


    // kategorie są unikalne na usera oraz globalne kategorie sa unikalne względem innych globalnych -- check
    // user nie może zduplikować kategorii globalnej (nie może stworzyc takiej która już istnieje globalna) -- check
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        String categoryName = categoryRequest.getName();

        if (categoryRepository.existsByNameIgnoreCaseAndUserIsNull(categoryName)) {
            // 409
            throw new CategoryAlreadyExistsException("Category already exists");
        }
        if (currentUserProvider.hasRole(UserRole.USER)
                && categoryRepository.existsByNameIgnoreCaseAndUserId(categoryName, currentUserProvider.getCurrentUserId())) {
            // 409
            throw new CategoryAlreadyExistsException("Category already exists");
        }
        Category category = categoryMapper.mapFromRequest(categoryRequest);
        category.setUser(currentUserProvider.hasRole(UserRole.EMPLOYEE) ? null : new User(currentUserProvider.getCurrentUser()));

        Category createdCategory = categoryRepository.save(category);
        return categoryMapper.mapToResponse(createdCategory);
    }

    // user nie może edytować nie swojej lub globalnej kategorii -- check
    // employee może edytować co chce -- check
    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchCategoryExistsException("No such category exists"));
        if (!currentUserProvider.hasRole(UserRole.EMPLOYEE)) {
            if (existingCategory.getUser() == null) {
                // 403
                throw new AccessDeniedException("Unauthorized access to category");
            }
            if (!currentUserProvider.isCurrentUser(existingCategory.getUser())) {
                // 403
                throw new AccessDeniedException("Unauthorized access to category");
            }
        }


        String categoryName = categoryRequest.getName();

        if (categoryRepository.existsByNameIgnoreCaseAndUserIsNullAndIdNot(categoryName, id)) {
            // 409
            throw new CategoryAlreadyExistsException("Category already exists");
        }

        if (!currentUserProvider.hasRole(UserRole.EMPLOYEE)
                && categoryRepository.existsByNameIgnoreCaseAndUserIdAndIdNot(categoryName, currentUserProvider.getCurrentUserId(), id)
        ) {
            // 409
            throw new CategoryAlreadyExistsException("Category already exists");
        }

        existingCategory.setName(categoryRequest.getName());
        Category updatedCategory =  categoryRepository.save(existingCategory);
        return categoryMapper.mapToResponse(updatedCategory);
    }

    // user nie może usuwać nie swojej lub globalnej kategorii -- check
    // employee może usuwać globalne kategorie -- check
    @Override
    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                //404
                .orElseThrow(() -> new NoSuchCategoryExistsException("No such category exists"));

        if (isCategoryGlobal(category)) {
            if (!currentUserProvider.hasRole(UserRole.EMPLOYEE)) {
                // 403
                throw new AccessDeniedException("Unauthorized access to category");
            }
            categoryRepository.deleteById(id);
            return;
        }

        if (!currentUserProvider.isCurrentUser(category.getUser())) {
            // 403
            throw new AccessDeniedException("Unauthorized access to category");
        }

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

    private boolean isCategoryGlobal(Category category) {
        return category.getUser() == null;
    }

}
