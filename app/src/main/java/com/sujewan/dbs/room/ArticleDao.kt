package com.sujewan.dbs.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sujewan.dbs.model.Article
import com.sujewan.dbs.model.ArticleDescription
import com.sujewan.dbs.util.Constants.Companion.RECORD_ID
import com.sujewan.dbs.util.Constants.Companion.RECORD_LAST_UPDATE
import com.sujewan.dbs.util.Constants.Companion.TABLE_ARTICLE_DES_RECORD
import com.sujewan.dbs.util.Constants.Companion.TABLE_ARTICLE_RECORD

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllArticles(articles: List<Article>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticleDescription(articleDesc: ArticleDescription)

    @Query("UPDATE $TABLE_ARTICLE_RECORD SET ${RECORD_LAST_UPDATE}=:lastUpdate WHERE ${RECORD_ID}=:id")
    fun updateArticleLastUpdate(id: Int, lastUpdate: Long)

    @Query("SELECT * FROM $TABLE_ARTICLE_DES_RECORD WHERE ${RECORD_ID}=:id")
    fun getArticleById(id: Int): LiveData<ArticleDescription>

    @Query("SELECT * FROM $TABLE_ARTICLE_RECORD ORDER BY $RECORD_LAST_UPDATE DESC")
    fun getAllArticles(): LiveData<List<Article>>
}