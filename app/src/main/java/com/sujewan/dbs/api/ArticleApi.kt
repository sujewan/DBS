package com.sujewan.dbs.api

import androidx.lifecycle.LiveData
import com.sujewan.dbs.model.Article
import com.sujewan.dbs.model.ArticleDescription
import retrofit2.http.GET
import retrofit2.http.Path

interface ArticleApi {
    @GET("article")
    fun getAllArticles(): LiveData<ApiResponse<List<Article>>>

    @GET("article/{id}")
    fun getArticleById(@Path("id") id: Int): LiveData<ApiResponse<ArticleDescription>>
}