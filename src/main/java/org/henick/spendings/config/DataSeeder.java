package org.henick.spendings.config;

import org.henick.spendings.model.User;
import org.henick.spendings.model.UserRole;
import org.henick.spendings.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createUserIfNotExists("employee@mail.com", "employee", "employee", UserRole.EMPLOYEE);
        createUserIfNotExists("user@mail.com", "user", "user", UserRole.USER);
    }

    private void createUserIfNotExists(String email, String rawPassword, String username, UserRole userRole) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            return;
        }
        userRepository.save(
                new User(email, passwordEncoder.encode(rawPassword), username, userRole)
        );
    }

}
