package com.example.articleserver.Service;

import com.example.articleserver.entity.ArticleEntity;
import com.example.articleserver.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<ArticleEntity> index() {
        return articleRepository.findAll();
    }

    public ArticleEntity show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }
}
