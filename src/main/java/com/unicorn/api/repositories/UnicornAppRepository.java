package com.unicorn.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unicorn.api.domain.unicornApp.UnicornApp;

@Repository
public interface UnicornAppRepository extends JpaRepository<UnicornApp, Long> {
    Optional<UnicornApp> findByOwnerId(Long ownerId);
}
