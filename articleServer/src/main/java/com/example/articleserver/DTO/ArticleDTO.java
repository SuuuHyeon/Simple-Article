package com.example.articleserver.DTO;


import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleDTO {
    private String title;
    private String content;
    private String maker;
    private String date;
}


