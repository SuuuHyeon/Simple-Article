package com.example.articleserver.controller;

import com.example.articleserver.Service.ArticleService;
import com.example.articleserver.entity.ArticleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/articles")
    public List<ArticleEntity> index() {
        return articleService.index();
    }
}
