package com.example.articleserver.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private String maker;

    public void patch(ArticleEntity article) {
        if (article.title != null)
            this.title = article.title;
        if (article.content != null)
            this.content = article.content;
        if (article.maker != null)
            this.maker = article.maker;
    }
//    @Column
//    private String date;
}
