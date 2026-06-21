package org.henick.spendings.service;

import org.henick.spendings.model.User;

public interface UserService {

    User getByEmailIgnoreCase(String email);
    User create(User user);
    boolean existsByEmailIgnoreCase(String email);

}
