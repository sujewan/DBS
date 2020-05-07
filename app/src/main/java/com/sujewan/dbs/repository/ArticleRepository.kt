package com.sujewan.dbs.repository

import androidx.lifecycle.LiveData
import com.sujewan.dbs.api.ApiResponse
import com.sujewan.dbs.api.ArticleApi
import com.sujewan.dbs.api.Resource
import com.sujewan.dbs.model.Article
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository @Inject
constructor(val articleApi: ArticleApi) {

    fun getArticles(): LiveData<Resource<List<Article>>> {

        return object : NetworkBoundResource<List<Article>, List<Article>>() {

            override fun fetchService(): LiveData<ApiResponse<List<Article>>> {
                return articleApi.getAllArticles()
            }

            override fun onFetchFailed() {

            }

            override fun gatherFetchData(item: List<Article>): List<Article> {
                return item
            }

        }.asLiveData

    }
}
