package com.sujewan.dbs.api

import androidx.lifecycle.LiveData
import com.sujewan.dbs.model.Article
import retrofit2.http.GET

interface ArticleApi {
    @GET("article")
    fun getAllArticles(): LiveData<ApiResponse<List<Article>>>
}