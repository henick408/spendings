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
        userRepository.save(new User("employee@mail.com", passwordEncoder.encode("employee"), "employee", UserRole.EMPLOYEE));
        userRepository.save(new User("user@mail.com", passwordEncoder.encode("user"), "user", UserRole.USER));
    }
}
