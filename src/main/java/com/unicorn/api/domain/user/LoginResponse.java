package com.unicorn.api.domain.user;

public record LoginResponse(Long id, String name, String email, String token, UserRole role) {

}
