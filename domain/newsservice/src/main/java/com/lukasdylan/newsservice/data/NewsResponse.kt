package com.lukasdylan.newsservice.data

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Keep
data class NewsResponse(
    @Json(name = "status")
    val status: String? = null,
    @Json(name = "totalResults")
    val totalResult: Int? = 0,
    @Json(name = "articles")
    val articles: List<Article> = mutableListOf()
)

@Keep
@Parcelize
data class Article(
    @Json(name = "source")
    val source: Source? = null,
    @Json(name = "author")
    val author: String? = null,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "url")
    val url: String? = null,
    @Json(name = "urlToImage")
    val urlToImage: String? = null,
    @Json(name = "publishedAt")
    val publishedAt: String? = null
) : Parcelable

@Keep
@Parcelize
data class Source(
    @Json(name = "id")
    val id: String? = null,
    @Json(name = "name")
    val name: String? = null
) : Parcelable

