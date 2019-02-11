package com.lukasdylan.football.ui.playerdetail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.google.android.material.appbar.AppBarLayout
import com.lukasdylan.core.extension.GlideTransformationMode
import com.lukasdylan.core.extension.loadImagesFromUrl
import com.lukasdylan.core.extension.observeValue
import com.lukasdylan.core.extension.titleTextView
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ActivityDetailPlayerBinding
import com.lukasdylan.football.utility.circleImageUrl
import org.koin.androidx.viewmodel.ext.android.getViewModel

class DetailPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDetailPlayerBinding>(this, R.layout.activity_detail_player)
        with(binding) {
            lifecycleOwner = this@DetailPlayerActivity
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            initTextViewToolbar(toolbar = toolbar, appBarLayout = appBarLayout)
            return@with getViewModel<DetailPlayerViewModel>().also {
                observeValue(it.toolbarTitle) { title -> supportActionBar?.title = title }
                observeValue(it.playerName) { name -> tvPlayerName.text = name }
                observeValue(it.playerPhotoBackground) { url ->
                    ivPlayerFanArt.loadImagesFromUrl(
                        url,
                        R.color.lighter_gray,
                        GlideTransformationMode.FULL_CENTER_CROP_IMAGE
                    )
                }
                observeValue(it.playerPosition) { position -> tvPlayerPosition.text = position }
                observeValue(it.playerPhotoUrl) { url -> ivPlayerPhoto.circleImageUrl(url) }

                intent?.extras?.let { bundle -> it.loadData(bundle) }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

    private fun initTextViewToolbar(toolbar: Toolbar, appBarLayout: AppBarLayout) {
        val toolbarTextView = toolbar.titleTextView()
        toolbarTextView?.alpha = 0f

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val alpha = Math.abs(verticalOffset).toFloat() / (appBarLayout.measuredHeight - toolbar.measuredHeight)
            if (alpha <= 0.5f) {
                toolbarTextView?.alpha = alpha
            } else {
                toolbarTextView?.alpha = 1.0f
            }
        })
    }
}