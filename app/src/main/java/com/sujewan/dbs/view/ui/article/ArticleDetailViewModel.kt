package com.sujewan.dbs.view.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sujewan.dbs.api.Resource
import com.sujewan.dbs.model.ArticleDescription
import com.sujewan.dbs.repository.ArticleRepository
import com.sujewan.dbs.room.ArticleDao
import javax.inject.Inject

class ArticleDetailViewModel @Inject
constructor(
    private val articleDao: ArticleDao,
    private val articleRepository: ArticleRepository) : ViewModel() {

    var articleId : Int = 0

    fun getArticleById(articleId: Int): LiveData<Resource<ArticleDescription>> {
        return articleRepository.getArticleById(articleId)
    }
}