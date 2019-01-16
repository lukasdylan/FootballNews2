package com.lukasdylan.news

import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.lukasdylan.core.extension.titleTextView
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
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        val bundle = intent.extras
        bundle?.let {
            val newsUrl = it.getString("news_url").orEmpty()
            val newsTitle = it.getString("news_title").orEmpty()
            binding.wvNews.loadUrl(newsUrl)
            supportActionBar?.title = newsTitle
            initTextViewToolbar(binding.toolbar)
        }
    }

    private fun initTextViewToolbar(toolbar: Toolbar) {
        val toolbarTextView = toolbar.titleTextView()
        toolbarTextView?.ellipsize = TextUtils.TruncateAt.MARQUEE
        toolbarTextView?.marqueeRepeatLimit = -1
        toolbarTextView?.isSelected = true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }
}