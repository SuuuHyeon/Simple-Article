package com.example.article

import androidx.compose.runtime.MutableState
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
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
    // 전체 조회
    @GET("/articles")
    suspend fun getArticleList(): Response<List<Article>>

    // 단일 조회(상세 페이지)
    @GET("/articles/{id}")
    suspend fun getArticle(@Path("id") id: String): Response<Article>

    // 수정
    @PATCH("articles/{id}")
    suspend fun updateArticle(@Path("id") id: String, @Body article: Article): Response<Article>

    // 생성
    @POST("articles")
    suspend fun createArticle(@Body article: Article): Response<Article>
}