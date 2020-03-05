package dev.blank.topheadline.data.model

import com.google.gson.annotations.SerializedName

class NewsResponse {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("articles")
    var newsList: List<News>? = null
}