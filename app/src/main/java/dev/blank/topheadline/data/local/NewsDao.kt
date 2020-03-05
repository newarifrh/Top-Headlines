package dev.blank.topheadline.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.blank.topheadline.data.model.News

@Dao
interface NewsDao {
    @Insert
    fun insert(news: News?)

    @Update
    fun update(news: News?)

    @Delete
    fun delete(news: News?)

    @Query("DELETE FROM news_table")
    fun deleteAllNews()

    @get:Query("SELECT * FROM news_table ORDER BY random()")
    val allNews: LiveData<List<News?>?>?

    @Insert
    fun insertList(newsList: List<News>?)
}