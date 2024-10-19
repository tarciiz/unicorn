package com.unicorn.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/admin")
public class AdminController {

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        return new ResponseEntity("Hello World", HttpStatus.OK);
    }

}
