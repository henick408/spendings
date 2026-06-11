package org.henick.spendings.controller;

import org.henick.spendings.dto.CategoryRequest;
import org.henick.spendings.dto.CategoryResponse;
import org.henick.spendings.mapper.CategoryMapper;
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
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        CategoryResponse category = categoryService.getById(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest) {
        if (categoryService.existsByNameIgnoreCase(categoryRequest.getName())) {
            return ResponseEntity.badRequest().body("Category with such name already exists");
        }
        CategoryResponse createdCategory = categoryService.create(categoryRequest);

        return ResponseEntity.created(URI.create("/api/categories/" + createdCategory.getId())).body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest) {
        if (!categoryService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        if (categoryService.existsByNameIgnoreCase(categoryRequest.getName())) {
            return ResponseEntity.badRequest().body("Category with such name already exists");
        }
        categoryService.update(id, categoryRequest);

        return ResponseEntity.ok("Category updated successfully");
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
