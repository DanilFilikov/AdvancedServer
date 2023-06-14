package com.example.filikov_advanced_server.repository;

import com.example.filikov_advanced_server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<UserEntity, UUID>{
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
}
