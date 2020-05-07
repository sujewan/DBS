package com.sujewan.dbs.model

import com.google.gson.annotations.SerializedName

data class ArticleDescription (
    @SerializedName("id")
    val id: Int,

    @SerializedName("text")
    val desc: String
)