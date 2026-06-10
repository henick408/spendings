package org.henick.spendings.repository;

import org.henick.spendings.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);

}
