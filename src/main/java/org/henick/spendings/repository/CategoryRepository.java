package org.henick.spendings.repository;

import org.henick.spendings.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
    // kategorie globalne
    boolean existsByNameIgnoreCaseAndUserIsNull(String name);
    // kategoria o nazwie danego usera (bo wiele userów może mieć takie same)
    boolean existsByNameIgnoreCaseAndUserId(String name, Long id);

    boolean existsByNameIgnoreCaseAndUserIsNullAndIdNot(String name, Long id);

    boolean existsByNameIgnoreCaseAndUserIdAndIdNot(String name, Long userId, Long id);
}
