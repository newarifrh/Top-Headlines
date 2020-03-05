package dev.blank.topheadline.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import dev.blank.topheadline.data.manager.NewsRepository
import dev.blank.topheadline.data.model.News

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    var repository: NewsRepository =
        NewsRepository(application)
    var allNews: LiveData<List<News?>?>?
    fun insert(news: News?) {
        repository.insert(news)
    }

    fun update(news: News?) {
        repository.update(news)
    }

    fun delete(news: News?) {
        repository.delete(news)
    }

    fun deleteAllNews() {
        repository.deleteAllNews()
    }

    fun getNewsService() {
        repository.getNewsService()
    }

    init {
        allNews = repository.allNews
    }
}