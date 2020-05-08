package com.sujewan.dbs.view.ui.edit

import androidx.lifecycle.ViewModel
import com.sujewan.dbs.model.ArticleDescription
import com.sujewan.dbs.room.ArticleDao
import java.util.*
import javax.inject.Inject

class ArticleEditViewModel @Inject
constructor(private val articleDao: ArticleDao) : ViewModel() {
    
    fun updateArticleDescription(articleDescription: ArticleDescription) {
        // Get the Current Date time to update last update
        val lastUpdate = Calendar.getInstance().timeInMillis / 1000

        articleDescription.let { data -> 
            articleDao.insertArticleDescription(data)
            articleDao.updateArticleLastUpdate(articleDescription.id, lastUpdate)
        }
    }

}