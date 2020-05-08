package com.sujewan.dbs.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sujewan.dbs.model.Article
import com.sujewan.dbs.model.ArticleDescription

@Database(
    entities = [(Article::class), (ArticleDescription::class)],
    version = 2,
    exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun articleDao() : ArticleDao
}