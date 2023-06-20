package com.example.filikov_advanced_server.repository;

import com.example.filikov_advanced_server.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagsRepo extends JpaRepository<TagEntity, UUID> {
}
