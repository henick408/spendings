package org.henick.spendings.repository;

import org.henick.spendings.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByEmailIgnoreCase(String email);
}
