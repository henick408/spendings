package org.henick.spendings.controller;

import org.henick.spendings.dto.CategoryRequest;
import org.henick.spendings.dto.CategoryResponse;
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

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        if (!categoryService.existsCategoryById(id)) {
            return ResponseEntity.notFound().build();
        }
        CategoryResponse category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest) {
        if (categoryService.existsCategoryByNameIgnoreCase(categoryRequest.getName())) {
            return ResponseEntity.badRequest().body("Category with such name already exists");
        }
        CategoryResponse createdCategory = categoryService.createCategory(categoryRequest);

        return ResponseEntity
                .created(URI.create("/api/categories/" + createdCategory.getId()))
                .body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        if (!categoryService.existsCategoryById(id)) {
            return ResponseEntity.notFound().build();
        }
        if (categoryService.existsCategoryByNameIgnoreCase(categoryRequest.getName())) {
            return ResponseEntity.badRequest().body("Category with such name already exists");
        }
        categoryService.updateCategory(id, categoryRequest);

        return ResponseEntity.ok("Category updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        if (!categoryService.existsCategoryById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }

}
