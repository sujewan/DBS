package com.sujewan.dbs.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sujewan.dbs.model.ArticleDescription

@Database(
    entities = [(ArticleDescription::class)],
    version = 1,
    exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun articleDao() : ArticleDao
}