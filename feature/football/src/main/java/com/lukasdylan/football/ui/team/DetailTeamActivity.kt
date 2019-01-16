package com.lukasdylan.football.ui.team

import android.animation.Animator
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lukasdylan.core.extension.observeValue
import com.lukasdylan.core.extension.titleTextView
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ActivityDetailTeamBinding
import com.lukasdylan.football.ui.team.adapter.TeamBannerAdapter
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailTeamActivity : AppCompatActivity() {

    private val viewModel by viewModel<DetailTeamViewModel>()

    private val bannerAdapter by lazy {
        TeamBannerAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDetailTeamBinding>(this, R.layout.activity_detail_team)
        with(binding) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            initTextViewToolbar(toolbar, appBarLayout, fabFavorite)
            lottieAnimationView.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {

                }

                override fun onAnimationEnd(animation: Animator?) {
                    lottieAnimationView.visibility = View.GONE
                    fabFavorite.setImageResource(R.drawable.icon_favorite_fill)
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {

                }

            })
            rvTeamBanner.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                val snapHelper = PagerSnapHelper()
                snapHelper.attachToRecyclerView(this)
                adapter = bannerAdapter
            }
            fabFavorite.onClick {
                fabFavorite.setImageResource(0)
                lottieAnimationView.visibility = View.VISIBLE
                lottieAnimationView.playAnimation()
            }
        }
        with(viewModel) {
            observeValue(toolbarTitle) { supportActionBar?.title = it }
            observeValue(bannerList) { bannerAdapter.addData(it) }
            intent?.extras?.let { return@with loadData(it) }
        }
    }

    private fun initTextViewToolbar(toolbar: Toolbar, appBarLayout: AppBarLayout, fab: FloatingActionButton) {
        val toolbarTextView = toolbar.titleTextView()
        toolbarTextView?.alpha = 0f

        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val alpha = Math.abs(verticalOffset).toFloat() / (appBarLayout.measuredHeight - toolbar.measuredHeight)
            if (alpha <= 0.5f) {
                toolbarTextView?.alpha = alpha
            } else if (alpha <= 0.7) {
                if (fab.isOrWillBeHidden) {
                    fab.show()
                }
            } else {
                toolbarTextView?.alpha = 1.0f
                if (fab.isOrWillBeShown) {
                    fab.hide()
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }
}