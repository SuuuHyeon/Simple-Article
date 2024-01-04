package com.example.article

import android.util.Log

suspend fun updateArticle(article: Article, articleID: String): Article? {
    val retrofitInstance = RetrofitInstance.getInstance().create(MyApi::class.java)
    val response = retrofitInstance.updateArticle(articleID, article)
    Log.d("업데이트 articleID 값 : ", articleID)
    Log.d("업데이트 response.body", response.toString())
    return if (response.isSuccessful) response.body() else null
}

suspend fun deleteArticle(articleID: String): Article? {
    val retrofitInstance = RetrofitInstance.getInstance().create(MyApi::class.java)
    val response = retrofitInstance.deleteArticle(articleID)
    Log.d("삭제 articleID : ", articleID)
    return if (response.isSuccessful) response.body() else null
}