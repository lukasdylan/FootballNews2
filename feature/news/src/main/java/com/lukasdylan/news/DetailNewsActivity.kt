package com.lukasdylan.news

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.lukasdylan.news.databinding.ActivityDetailNewsBinding


class DetailNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDetailNewsBinding>(this, R.layout.activity_detail_news)
        with(binding) {
            wvNews.apply {
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
                settings.builtInZoomControls = false
                settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
                settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                settings.domStorageEnabled = true
                settings.setAppCacheEnabled(true)
                scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
                isScrollbarFadingEnabled = true
            }
            setSupportActionBar(toolbar)
            return@with supportActionBar?.let {
                it.title = "Football News 2"
                it.setDisplayHomeAsUpEnabled(true)
            }
        }

        val bundle = intent.extras
        bundle?.let {
            val newsUrl = it.getString("news_url") ?: ""
            binding.wvNews.loadUrl(newsUrl)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }
}