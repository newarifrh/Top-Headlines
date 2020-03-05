package dev.blank.topheadline.data.remote

import dev.blank.topheadline.data.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v2/top-headlines")
    fun getTopHeadline(
        @Query("country") country: String?, @Query(
            "pageSize"
        ) pageSize: Int
    ): Call<NewsResponse?>?

    @GET("v2/top-headlines")
    fun searchNews(
        @Query("q") q: String?, @Query("country") country: String?, @Query(
            "pageSize"
        ) pageSize: Int
    ): Call<NewsResponse?>?
}