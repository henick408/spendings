package org.henick.spendings.service;

import org.henick.spendings.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();
    Category getById(Long id);
    Category getByNameIgnoreCase(String name);
    Category create(Category category);
    Category update(Long id, Category category);
    void delete(Category category);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByNameIgnoreCase(String name);

}
