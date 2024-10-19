package com.unicorn.api.domain.user;

public record RegisterRequest(String name, String email, String password, UserRole role) {

}
