package com.example.filikov_advanced_server.repository;

import com.example.filikov_advanced_server.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NewsRepo extends JpaRepository<NewsEntity, UUID> {

}
