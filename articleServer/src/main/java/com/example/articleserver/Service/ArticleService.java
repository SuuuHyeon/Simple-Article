package com.example.articleserver.Service;

import com.example.articleserver.DTO.ArticleDTO;
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

    public ArticleEntity update(Long id, ArticleDTO dto) {
        ArticleEntity article = dto.toEntity();
        ArticleEntity target = articleRepository.findById(id).orElse(null);
        if (target == null || id != article.getId())
            return null;
        target.patch(article);
        ArticleEntity updated = articleRepository.save(target);
        return updated;
    }

    public ArticleEntity create(ArticleDTO dto) {
        ArticleEntity article = dto.toEntity();
        if (article.getId() != null)
            return null;
        return articleRepository.save(article);
    }

    public ArticleEntity delete(Long id) {
        ArticleEntity target = articleRepository.findById(id).orElse(null);
        if (target == null)
            return null;
        articleRepository.delete(target);
        return target;
    }
}
