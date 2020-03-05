package dev.blank.topheadline.view.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import dev.blank.topheadline.R
import dev.blank.topheadline.databinding.ActivityWebviewBinding


class WebviewActivity : AppCompatActivity() {
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        setupView()
    }

    private fun initData() {
        url = intent.getStringExtra("url")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupView() {
        val activityWebviewBinding: ActivityWebviewBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_webview)
        val webView = activityWebviewBinding.webView
        webView.webViewClient = WebViewClient()
        val set: WebSettings = webView.settings
        set.javaScriptEnabled = true
        set.builtInZoomControls = true
        webView.loadUrl(url)
    }
}