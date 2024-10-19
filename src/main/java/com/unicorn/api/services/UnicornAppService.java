package com.unicorn.api.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.unicorn.api.domain.unicornApp.UnicornApp;
import com.unicorn.api.domain.user.User;
import com.unicorn.api.infra.configs.UserFolderCreator;
import com.unicorn.api.infra.exceptions.BusinessException;
import com.unicorn.api.repositories.UnicornAppRepository;
import com.unicorn.api.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UnicornAppService {

    private final UnicornAppRepository unicornAppRepository;

    public ResponseEntity<?> save(UnicornApp app) {
        app = unicornAppRepository.save(app);

        UserFolderCreator.createAppFolder(app.getId(), app.getOwnerId());

        return ResponseEntity.ok().body(app);
    }

    public ResponseEntity<?> update(UnicornApp app) {
        return ResponseEntity.ok().body(unicornAppRepository.save(app));
    }

    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(unicornAppRepository.findAll());
    }

    public ResponseEntity<?> findByOwnerId(Long ownerId) {
        return ResponseEntity.ok().body(unicornAppRepository.findByOwnerId(ownerId));
    }
}
