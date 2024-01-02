package com.example.article

import androidx.compose.runtime.MutableState
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

data class Article(
    val id: String, val title: String, val content: String, val maker: String
)

object RetrofitInstance {

    val BASE_URL = "http://10.200.73.141:8080"

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

    @PATCH("articles/{id}")
    suspend fun updateArticle(@Path("id") id: String, @Body updatedArticle: Article): Response<Article>
}