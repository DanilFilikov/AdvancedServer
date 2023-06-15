package com.example.filikov_advanced_server.repository;

import com.example.filikov_advanced_server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<UserEntity, UUID>{

    @Transactional()
    @Query("select u from UserEntity u where u.email = :email")
    Optional<UserEntity> findByEmail(@Param("email") String email);

    @Transactional
    @Query("select case when count(u) > 0 then true else false end from UserEntity u where u.email = :email")
    Boolean existsByEmail(@Param("email") String email);
}
