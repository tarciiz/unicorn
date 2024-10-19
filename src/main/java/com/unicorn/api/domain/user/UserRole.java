package com.unicorn.api.domain.user;

public enum UserRole {
    ADMIN("ADMIN"),
    SUPERADMIN("SUPERADMIN");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
