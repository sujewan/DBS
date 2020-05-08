package com.sujewan.dbs.repository

import androidx.lifecycle.LiveData
import com.sujewan.dbs.api.ApiResponse
import com.sujewan.dbs.api.ArticleApi
import com.sujewan.dbs.api.Resource
import com.sujewan.dbs.model.Article
import com.sujewan.dbs.model.ArticleDescription
import com.sujewan.dbs.room.ArticleDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRepository @Inject
constructor(val articleDao: ArticleDao, val articleApi: ArticleApi) {

    fun getArticles(): LiveData<Resource<List<Article>>> {

        return object : NetworkBoundResource<List<Article>, List<Article>>() {

            override fun saveFetchData(item: List<Article>) {
                item.let { articleList ->
                    articleDao.insertAllArticles(articleList)
                }
            }

            override fun shouldFetch(data: List<Article>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Article>> {
                return articleDao.getAllArticles()
            }

            override fun fetchService(): LiveData<ApiResponse<List<Article>>> {
                return articleApi.getAllArticles()
            }


        }.asLiveData
    }

    fun getArticleById(articleId: Int): LiveData<Resource<ArticleDescription>> {

        return object : NetworkBoundResource<ArticleDescription, ArticleDescription>() {

            override fun saveFetchData(item: ArticleDescription) {
                item.let { articleList ->
                    articleDao.insertArticleDescription(articleList)
                }
            }

            override fun shouldFetch(data: ArticleDescription?): Boolean {
                return data == null
            }

            override fun loadFromDb(): LiveData<ArticleDescription> {
                return articleDao.getArticleById(articleId)
            }

            override fun fetchService(): LiveData<ApiResponse<ArticleDescription>> {
                return articleApi.getArticleById(articleId)
            }

        }.asLiveData
    }
}
