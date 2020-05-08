package com.sujewan.dbs.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sujewan.dbs.util.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Constants.TABLE_ARTICLE_RECORD)
data class Article (
    @PrimaryKey
    @ColumnInfo(name = Constants.RECORD_ID)
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = Constants.RECORD_TITLE)
    @SerializedName("title")
    val title: String,

    @ColumnInfo(name = Constants.RECORD_LAST_UPDATE)
    @SerializedName("last_update")
    val lastUpdate: Long,

    @ColumnInfo(name = Constants.RECORD_SHORT_DES)
    @SerializedName("short_description")
    val shortDes: String,

    @ColumnInfo(name = Constants.RECORD_AVATAR)
    @SerializedName("avatar")
    val avatar: String
): Parcelable