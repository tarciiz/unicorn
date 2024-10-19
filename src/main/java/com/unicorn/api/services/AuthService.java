package com.unicorn.api.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.unicorn.api.domain.user.LoginResponse;
import com.unicorn.api.domain.user.RegisterRequest;
import com.unicorn.api.domain.user.LoginRequest;
import com.unicorn.api.domain.user.User;
import com.unicorn.api.infra.configs.UserFolderCreator;
import com.unicorn.api.infra.exceptions.AuthException;
import com.unicorn.api.repositories.AuthRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private final AuthRepository authRepository;
    private final TokenService tokenService;

    public ResponseEntity<?> login(LoginRequest user, AuthenticationManager authManager) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(user.email(), user.password());

        try {
            authManager.authenticate(usernamePassword);
        } catch (Exception exception) {
            throw new AuthException("Usuário ou senha inválidos.");
        }

        var userDB = (User) authRepository.findByEmail(user.email());
        var token = tokenService.generateToken(user.email());

        var response = new LoginResponse(
                userDB.getId(), user.email(), user.email(), token, userDB.getRole());

        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<?> register(RegisterRequest user) {
        if (authRepository.existsByEmail(user.email())) {
            throw new AuthException("Usuário já existe.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(user.password());
        User userSaved = authRepository.save(new User(user.name(), user.email(), encryptedPassword, user.role()));

        UserFolderCreator.createUserFolder(userSaved.getId());

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> updateUserRistred(User user) {
        var userDB = authRepository.findUserByEmail(user.getEmail())
                .orElseThrow(() -> new AuthException("Usuário não encontrado."));

        userDB.setName(user.getName());
        userDB.setRole(user.getRole());
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        userDB.setPassword(encryptedPassword);

        return ResponseEntity.ok().body(authRepository.save(userDB));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authRepository.findByEmail(username);
    }

}
