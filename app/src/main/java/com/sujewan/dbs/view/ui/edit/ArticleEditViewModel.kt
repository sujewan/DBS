package com.sujewan.dbs.view.ui.edit

import androidx.lifecycle.ViewModel
import com.sujewan.dbs.model.ArticleDescription
import com.sujewan.dbs.room.ArticleDao
import javax.inject.Inject

class ArticleEditViewModel @Inject
constructor(private val articleDao: ArticleDao) : ViewModel() {
    
    fun updateArticleDescription(articleDescription: ArticleDescription) {
        articleDescription.let { data -> 
            articleDao.insertArticleDescription(data)
        }
    }

}