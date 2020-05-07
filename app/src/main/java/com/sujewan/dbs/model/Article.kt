package com.sujewan.dbs.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article (
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("last_update")
    val lastUpdate: Long,

    @SerializedName("short_description")
    val shortDes: String,

    @SerializedName("avatar")
    val avatar: String
): Parcelable