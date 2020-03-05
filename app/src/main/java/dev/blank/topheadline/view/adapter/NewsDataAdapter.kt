package dev.blank.topheadline.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import dev.blank.topheadline.R
import dev.blank.topheadline.data.model.News
import dev.blank.topheadline.databinding.NewsListItemBinding
import dev.blank.topheadline.view.ui.WebviewActivity


class NewsDataAdapter : RecyclerView.Adapter<NewsDataAdapter.NewsViewHolder>() {
    private var newsList: List<News?>? = null
    private var context: Context? = null
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): NewsViewHolder {
        val newsListItemBinding: NewsListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.context),
            R.layout.news_list_item, viewGroup, false
        )
        context = viewGroup.context
        return NewsViewHolder(newsListItemBinding)
    }

    override fun onBindViewHolder(
        newsViewHolder: NewsViewHolder,
        i: Int
    ) {
        val news = newsList!![i]
        newsViewHolder.newsListItemBinding.news = news
        newsViewHolder.newsListItemBinding.root.findViewById<CardView>(R.id.cardContent)
            .setOnClickListener {
                val intent = Intent(context, WebviewActivity::class.java)
                intent.putExtra("url", news!!.url)
                context!!.startActivity(intent)
            }
    }


    override fun getItemCount(): Int {
        return if (newsList != null) {
            newsList!!.size
        } else {
            0
        }
    }

    fun setNewsList(news: List<News?>?) {
        this.newsList = news
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(newstListItemBinding: NewsListItemBinding) :
        RecyclerView.ViewHolder(newstListItemBinding.root) {
        val newsListItemBinding: NewsListItemBinding = newstListItemBinding

    }


}