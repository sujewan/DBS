package com.sujewan.dbs.view.ui.home

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sujewan.dbs.model.Article
import java.text.SimpleDateFormat
import java.util.*

class ItemArticleViewModel(private val article: Article) : BaseObservable() {

    @Bindable
    fun getTitle(): String = article.title.capitalize()

    @Bindable
    fun getShortDes(): String = article.shortDes.capitalize()

    @Bindable
    fun getLastUpdate(): String {
        val date = Date(article.lastUpdate * 1000)
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return format.format(date)
    }

    var imageUrl = article.avatar

}