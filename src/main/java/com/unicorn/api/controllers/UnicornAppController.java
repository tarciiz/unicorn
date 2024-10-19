package com.unicorn.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicorn.api.domain.unicornApp.UnicornApp;
import com.unicorn.api.domain.user.User;
import com.unicorn.api.services.UnicornAppService;
import com.unicorn.api.services.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/unicorn/apps")
public class UnicornAppController {
    private final UnicornAppService unicornAppService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody UnicornApp app) {
        return unicornAppService.save(app);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UnicornApp app) {
        return unicornAppService.update(app);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findByEmail(@PathVariable("userId") Long userId) {
        return unicornAppService.findByOwnerId(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return unicornAppService.findAll();
    }

}
