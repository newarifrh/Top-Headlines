package dev.blank.topheadline.view.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.blank.topheadline.R
import dev.blank.topheadline.databinding.ActivityNewsBinding
import dev.blank.topheadline.view.adapter.NewsDataAdapter
import dev.blank.topheadline.viewmodel.NewsViewModel
import dev.blank.topheadline.viewmodel.NewsViewModelFactory


class NewsActivity : AppCompatActivity() {
    private var newsViewModel: NewsViewModel? = null
    private var adapter: NewsDataAdapter? = null
    private var loading: ProgressBar? = null
    private var activityNewsBinding: ActivityNewsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNewsBinding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        setupView()
        setupViewModel()
        generateNews()
    }

    private fun generateNews() {
        newsViewModel!!.getNewsService()
    }

    private fun setupViewModel() {
        newsViewModel = ViewModelProvider(
            this,
            NewsViewModelFactory(application)
        ).get(NewsViewModel::class.java)
        newsViewModel!!.allNews!!
            .observe(this, Observer { news -> adapter!!.setNewsList(news) })
    }


    private fun setupView() {
        val recyclerView: RecyclerView = activityNewsBinding!!.recyclerView
        loading = activityNewsBinding!!.loading
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        adapter = NewsDataAdapter()
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Handle item selection
        return when (item.itemId) {
            R.id.menu_sync -> {
                newsViewModel!!.getNewsService()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}