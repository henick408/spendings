package org.henick.spendings.service;

import org.henick.spendings.model.User;
import org.henick.spendings.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getByEmailIgnoreCase(String email) {
        return userRepository.getByEmailIgnoreCase(email).orElse(null);
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }
}
