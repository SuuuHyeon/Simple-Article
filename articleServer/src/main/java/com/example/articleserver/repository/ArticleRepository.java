package com.example.articleserver.repository;

import com.example.articleserver.entity.ArticleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository extends CrudRepository<ArticleEntity, Long> {
    @Override
    ArrayList<ArticleEntity> findAll();
}
