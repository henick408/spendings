package org.henick.spendings.controller;

import org.henick.spendings.dto.CategoryRequest;
import org.henick.spendings.dto.CategoryResponse;
import org.henick.spendings.mapper.CategoryMapper;
import org.henick.spendings.model.Category;
import org.henick.spendings.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@PreAuthorize("hasAnyRole('USER', 'EMPLOYEE')")
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.getAll();
        return ResponseEntity.ok(categories.stream().map(categoryMapper::mapToResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryMapper.mapToResponse(category));
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest) {
        if (categoryService.existsByNameIgnoreCase(categoryRequest.getName())) {
            return ResponseEntity.badRequest().body("Category with such name already exists");
        }
        Category category = categoryMapper.mapFromRequest(categoryRequest);
        Category createdCategory = categoryService.create(category);
        CategoryResponse response = categoryMapper.mapToResponse(createdCategory);

        return ResponseEntity.created(URI.create("/api/categories/" + createdCategory.getId())).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        if (!categoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Category category = categoryMapper.mapFromRequest(categoryRequest);
        categoryService.update(id, category);

        return  ResponseEntity.ok("Category updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        if (!categoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
