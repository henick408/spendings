package org.henick.spendings.service;

import org.henick.spendings.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();
    User getById(Long id);
    User getByEmailIgnoreCase(String email);
    User create(User user);
    void delete(User user);
    void deleteById(Long id);
    User update(Long id, User user);
    boolean existsById(Long id);

}
