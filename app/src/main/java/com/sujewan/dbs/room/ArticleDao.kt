package com.sujewan.dbs.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sujewan.dbs.model.ArticleDescription
import com.sujewan.dbs.util.Constants

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticleDescription(articleDesc: ArticleDescription)

    @Query("SELECT * FROM ${Constants.TABLE_ARTICLE_DES_RECORD} WHERE ${Constants.RECORD_ID}=:id")
    fun getArticleById(id: Int): ArticleDescription
}