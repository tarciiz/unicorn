package com.unicorn.api.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.unicorn.api.domain.user.User;
import com.unicorn.api.infra.exceptions.BusinessException;
import com.unicorn.api.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<?> save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException("E-mail já registrado.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);

        return ResponseEntity.ok().body(userRepository.save(user));
    }

    public ResponseEntity<?> update(User user) {
        return ResponseEntity.ok().body(userRepository.save(user));
    }

    public ResponseEntity<?> findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));

        return ResponseEntity.ok().body(user);
    }

    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(userRepository.findAll());
    }
}
