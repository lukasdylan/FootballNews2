package com.lukasdylan.news.ui.detailnews

import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.lukasdylan.core.extension.titleTextView
import com.lukasdylan.news.R
import com.lukasdylan.news.databinding.ActivityDetailNewsBinding


class DetailNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDetailNewsBinding>(
            this,
            R.layout.activity_detail_news
        )
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
            initTextViewToolbar(toolbar)
        }

        intent.extras?.let {
            binding.wvNews.loadUrl(it.getString("news_url").orEmpty())
            supportActionBar?.title = it.getString("news_title").orEmpty()
        }
    }

    private fun initTextViewToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.titleTextView()?.apply {
            ellipsize = TextUtils.TruncateAt.MARQUEE
            marqueeRepeatLimit = -1
            isSelected = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }
}