package com.unicorn.api.infra.configs;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.unicorn.api.domain.user.User;
import com.unicorn.api.domain.user.UserRole;
import com.unicorn.api.repositories.AuthRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@Transactional
public class DataInitializer implements CommandLineRunner {

    private final BCryptPasswordEncoder encoder;
    private final AuthRepository authRepository;

    @Override
    public void run(String... args) {
        System.out.println("######## Initializing Data ########");
        initializeData();
        System.out.println("######## Data Initialized ########");
    }

    public void initializeData() {
        if (authRepository.findByRole(UserRole.ADMIN).isEmpty()) {
            User superAdmin = createSuperAdmin();
        }
    }

    private User createSuperAdmin() {
        var user = new User(
                "SuperAdmin",
                "super_adm@mail.com",
                encoder.encode("super123"),
                UserRole.SUPERADMIN);
        return authRepository.save(user);
    }
}
