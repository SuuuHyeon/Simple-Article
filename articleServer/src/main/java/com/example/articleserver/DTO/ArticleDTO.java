package com.example.articleserver.DTO;


import com.example.articleserver.entity.ArticleEntity;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private String maker;

    public ArticleEntity toEntity() {
        return new ArticleEntity(id, title, content, maker);
    }
}


