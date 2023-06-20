package com.example.filikov_advanced_server.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    private String description;
    private String image;
    @OneToMany(targetEntity = TagEntity.class)
    private List<TagEntity> tags;
    private String title;

}