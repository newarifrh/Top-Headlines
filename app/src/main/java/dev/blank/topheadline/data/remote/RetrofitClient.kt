package dev.blank.topheadline.data.remote

import dev.blank.topheadline.data.manager.Config
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val url = "http://newsapi.org/"
    private val retrofit: Retrofit
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
            val httpClient = OkHttpClient.Builder().addInterceptor(interceptor)
            httpClient.addInterceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("X-Api-Key", Config.API_KEY)
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            val client = httpClient.build()
            return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

    val newsService: NewsService
        get() = retrofit.create(
            NewsService::class.java
        )
}