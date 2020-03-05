package dev.blank.topheadline.view.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.blank.topheadline.R
import dev.blank.topheadline.databinding.ActivityWebviewBinding
import dev.blank.topheadline.viewmodel.WebviewViewModel
import dev.blank.topheadline.viewmodel.WebviewViewModelFactory


class WebviewActivity : AppCompatActivity() {
    private var url: String? = null
    private var webView: WebView? = null
    private var webiviewViewModel: WebviewViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        setupView()
        setupViewModel()
    }

    private fun setupViewModel() {
        webiviewViewModel = ViewModelProvider(
            this,
            WebviewViewModelFactory(application)
        ).get(WebviewViewModel::class.java)

        webiviewViewModel!!.url!!.value = url

        webiviewViewModel!!.url!!.observe(this, Observer { url ->
            webView!!.loadUrl(url)
        })
    }

    private fun initData() {
        url = intent.getStringExtra("url")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupView() {
        val activityWebviewBinding: ActivityWebviewBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_webview)
        webView = activityWebviewBinding.webView
        webView!!.webViewClient = WebViewClient()
        webView!!.settings.javaScriptEnabled = true
        webView!!.settings.builtInZoomControls = true
    }
}