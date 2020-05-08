package com.sujewan.dbs.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleDescription (
    @SerializedName("id")
    val id: Int,

    @SerializedName("text")
    val desc: String
): Parcelable