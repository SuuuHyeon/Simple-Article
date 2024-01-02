package com.example.article

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class Article(
    val id: String, val title: String, val content: String, val maker: String
)

object RetrofitInstance {

    val BASE_URL = "http://192.168.0.10:8080"

    val client = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getInstance(): Retrofit {
        return client
    }
}

interface MyApi {
    @GET("/articles")
    suspend fun getArticleList(): Response<List<Article>>

    @GET("/articles/{id}")
    suspend fun getArticle(@Path("id") id: String): Response<Article>
}