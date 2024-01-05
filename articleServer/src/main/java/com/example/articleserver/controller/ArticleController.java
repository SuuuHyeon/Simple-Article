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
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    // 게시글 조회
    @GetMapping("/articles")
    public List<ArticleEntity> index() {
        return articleService.index();
    }

    // 게시글 단일 조회
    @GetMapping("/articles/{id}")
    public ArticleEntity show(@PathVariable Long id) {
        return articleService.show(id);
    }

    // 게시글 생성
    @PostMapping("/articles")
    public ResponseEntity<ArticleEntity> create(@RequestBody ArticleDTO dto) {
        ArticleEntity created = articleService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 게시글 수정
    @PatchMapping("/articles/{id}")
    public ResponseEntity<ArticleEntity> update(@PathVariable Long id, @RequestBody ArticleDTO dto) {
        ArticleEntity updated = articleService.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 게시글 삭제
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<ArticleEntity> delete(@PathVariable Long id) {
        ArticleEntity deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.OK).body(deleted) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
