package com.example.filikov_advanced_server.repository;

import com.example.filikov_advanced_server.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepo extends JpaRepository<LogEntity, Long> {
}
