package dev.blank.topheadline.data.manager

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import dev.blank.topheadline.data.local.NewsDao
import dev.blank.topheadline.data.local.NewsDatabase
import dev.blank.topheadline.data.model.News
import dev.blank.topheadline.data.model.NewsResponse
import dev.blank.topheadline.data.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback

class NewsRepository(application: Application?) {
    private val newsDao: NewsDao
    var allNews: LiveData<List<News?>?>?
    fun insert(news: News?) {
        InsertNewsAsyncTask(
            newsDao
        ).execute(news)
    }

    fun insertNewsList(news: List<News>?) {
        InsertNewsListAsyncTask(
            newsDao
        ).execute(news)
    }

    fun update(news: News?) {
        UpdateNewsAsyncTask(
            newsDao
        ).execute(news)
    }

    fun delete(news: News?) {
        DeleteNewsAsyncTask(
            newsDao
        ).execute(news)
    }

    fun deleteAllNews() {
        DeleteAllNewsAsyncTask(
            newsDao
        ).execute()
    }

    private class InsertNewsListAsyncTask(private val newsDao: NewsDao) :
        AsyncTask<List<News>?, Void?, Void?>() {

        override fun doInBackground(vararg params: List<News>?): Void? {
            try {
                newsDao.insertList(params[0])
            } catch (e: SQLiteConstraintException) {
                Log.e("Top-Headline", e.message!!)
            }
            return null
        }
    }

    private class InsertNewsAsyncTask(private val newsDao: NewsDao) :
        AsyncTask<News?, Void?, Void?>() {
        override fun doInBackground(vararg params: News?): Void? {
            try {
                newsDao.insert(params[0])
            } catch (e: SQLiteConstraintException) {
                Log.e("Top-Headline", e.message!!)
            }
            return null
        }
    }

    private class UpdateNewsAsyncTask(private val newsDao: NewsDao) :
        AsyncTask<News?, Void?, Void?>() {
        override fun doInBackground(vararg news: News?): Void? {
            newsDao.update(news[0])
            return null
        }

    }

    private class DeleteNewsAsyncTask(private val newsDao: NewsDao) :
        AsyncTask<News?, Void?, Void?>() {
        override fun doInBackground(vararg news: News?): Void? {
            newsDao.delete(news[0])
            return null
        }

    }

    private class DeleteAllNewsAsyncTask(private val newsDao: NewsDao) :
        AsyncTask<News?, Void?, Void?>() {
        override fun doInBackground(vararg news: News?): Void? {
            newsDao.deleteAllNews()
            return null
        }

    }

    init {
        val database = NewsDatabase.getInstance(application!!)
        newsDao = database!!.newsDao()
        allNews = newsDao.allNews
    }


    fun getNewsService() {
        RetrofitClient.newsService.getTopHeadline("id", 10)!!
            .enqueue(object : Callback<NewsResponse?> {
                override fun onResponse(
                    call: Call<NewsResponse?>,
                    response: retrofit2.Response<NewsResponse?>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.status == "ok") {
                            deleteAllNews()
                            insertNewsList(response.body()!!.newsList)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<NewsResponse?>,
                    t: Throwable
                ) {

                }
            })
    }
}