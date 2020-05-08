package com.sujewan.dbs.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.sujewan.dbs.util.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Constants.TABLE_ARTICLE_DES_RECORD)
data class ArticleDescription (
    @PrimaryKey
    @ColumnInfo(name = Constants.RECORD_ID)
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = Constants.RECORD_DESCRIPTION)
    @SerializedName("text")
    var desc: String
): Parcelable