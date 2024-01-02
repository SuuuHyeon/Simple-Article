package com.example.articleserver.controller;

import com.example.articleserver.DTO.ArticleDTO;
import com.example.articleserver.Service.ArticleService;
import com.example.articleserver.entity.ArticleEntity;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/articles")
    public List<ArticleEntity> index() {
        return articleService.index();
    }

    @GetMapping("/articles/{id}")
    public ArticleEntity show(@PathVariable Long id) {
        return articleService.show(id);
    }

    @PatchMapping("/articles/{id}")
    public ResponseEntity<ArticleEntity> update(@PathVariable Long id, @RequestBody ArticleDTO dto) {
        ArticleEntity updated = articleService.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
