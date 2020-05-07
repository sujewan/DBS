package com.sujewan.dbs.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sujewan.dbs.api.Resource
import com.sujewan.dbs.model.Article
import com.sujewan.dbs.repository.ArticleRepository
import com.sujewan.dbs.view.adapter.ArticlesAdapter
import javax.inject.Inject

class HomeActivityViewModel @Inject
constructor(articleRepository: ArticleRepository) : ViewModel() {

    var articlesLiveData: LiveData<Resource<List<Article>>> = MutableLiveData()

    lateinit var adapter : ArticlesAdapter
    var firstTime = false

    init {
        articlesLiveData = articleRepository.getArticles()
    }
}