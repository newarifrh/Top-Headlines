package dev.blank.topheadline.view.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import dev.blank.topheadline.R
import dev.blank.topheadline.data.manager.Config
import dev.blank.topheadline.databinding.ActivityNewsBinding
import dev.blank.topheadline.view.adapter.NewsDataAdapter
import dev.blank.topheadline.viewmodel.NewsViewModel
import dev.blank.topheadline.viewmodel.NewsViewModelFactory
import kotlinx.android.synthetic.main.activity_news.*


class NewsActivity : AppCompatActivity() {
    private var newsViewModel: NewsViewModel? = null
    private var adapter: NewsDataAdapter? = null
    private var loading: ProgressBar? = null
    private var activityNewsBinding: ActivityNewsBinding? = null
    private var currentNativeAd: UnifiedNativeAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityNewsBinding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        setupView()
        setupAds()
        setupViewModel()
        generateNews()
    }

    private fun setupAds() {
        MobileAds.initialize(this) {}

        val builder = AdLoader.Builder(this, Config.ADS)

        builder.forUnifiedNativeAd { unifiedNativeAd ->
            // OnUnifiedNativeAdLoadedListener implementation.
            val adView = layoutInflater
                .inflate(
                    R.layout.news_ads,
                    window.decorView.rootView as ViewGroup,
                    false
                ) as UnifiedNativeAdView
            populateUnifiedNativeAdView(unifiedNativeAd, adView)
            layoutContent.addView(adView)
        }


        val adLoader = builder.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(errorCode: Int) {
            }
        }).build()

        adLoader.loadAd(AdRequest.Builder().build())
    }


    private fun populateUnifiedNativeAdView(
        nativeAd: UnifiedNativeAd,
        adView: UnifiedNativeAdView
    ) {
        // You must call destroy on old ads when you are done with them,
        // otherwise you will have a memory leak.
        currentNativeAd?.destroy()
        currentNativeAd = nativeAd
        // Set the media view.

        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)


        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            adView.bodyView.visibility = View.INVISIBLE
        } else {
            adView.bodyView.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.icon == null) {
            adView.iconView.visibility = View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                nativeAd.icon.drawable
            )
            adView.iconView.visibility = View.VISIBLE
        }


        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd)
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