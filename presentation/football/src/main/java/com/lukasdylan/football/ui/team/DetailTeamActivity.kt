package com.lukasdylan.football.ui.team

import android.animation.Animator
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.appbar.AppBarLayout
import com.lukasdylan.core.extension.observeValue
import com.lukasdylan.core.extension.titleTextView
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ActivityDetailTeamBinding
import com.lukasdylan.football.ui.team.adapter.MainTeamInfoAdapter
import com.lukasdylan.football.ui.team.adapter.NAVIGATE_BROWSER
import com.lukasdylan.football.ui.team.adapter.TeamBannerAdapter
import org.jetbrains.anko.browse
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailTeamActivity : AppCompatActivity(), Animator.AnimatorListener {

    private var menuFavorite: MenuItem? = null
    private lateinit var favoriteAnimationView: LottieAnimationView
    private lateinit var favoriteIconImageView: ImageView

    private val viewModel by viewModel<DetailTeamViewModel>()

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityDetailTeamBinding>(this, R.layout.activity_detail_team)
    }

    private val bannerAdapter by lazy { TeamBannerAdapter() }

    private val mainTeamInfoAdapter by lazy {
        MainTeamInfoAdapter {
            when (it.navigationId) {
                NAVIGATE_BROWSER -> {
                    val url = it.params?.get(0)?.second as? String
                    browse("https://" + url.orEmpty())
                }
                else -> {

                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            initTextViewToolbar()
            lottieAnimationView.addAnimatorListener(this@DetailTeamActivity)
            rvTeamBanner.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                val snapHelper = PagerSnapHelper()
                snapHelper.attachToRecyclerView(this)
                adapter = bannerAdapter
            }
            rvTeamInformation.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = mainTeamInfoAdapter
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
            observeValue(detailTeam) { mainTeamInfoAdapter.updateTeamInfo(it) }
            observeValue(playerList) { mainTeamInfoAdapter.updateTeamPlayers(it) }
            intent?.extras?.let { return@with loadData(it) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_match, menu)
        menuFavorite = menu?.findItem(R.id.menu_favorites)
        menuFavorite?.isVisible = false
        menuFavorite?.actionView?.apply {
            onClick {
                onOptionsItemSelected(menuFavorite)
            }
            favoriteAnimationView = find(R.id.lottieAnimationView)
            favoriteIconImageView = find(R.id.favoriteIcon)

            favoriteAnimationView.addAnimatorListener(this@DetailTeamActivity)
        }
//        viewModel.checkFavoriteMatch()
        return true
    }

    private fun initTextViewToolbar() {
        val toolbarTextView = binding.toolbar.titleTextView()
        toolbarTextView?.alpha = 0f

        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val alpha =
                Math.abs(verticalOffset).toFloat() / (binding.appBarLayout.measuredHeight - binding.toolbar.measuredHeight)
            if (alpha <= 0.5f) {
                toolbarTextView?.alpha = alpha
            } else if (alpha <= 0.7) {
                if (binding.fabFavorite.isOrWillBeHidden) {
                    binding.fabFavorite.show()
                    menuFavorite?.isVisible = false
                }
            } else {
                toolbarTextView?.alpha = 1.0f
                if (binding.fabFavorite.isOrWillBeShown) {
                    binding.fabFavorite.hide()
                    menuFavorite?.isVisible = true
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when {
            item?.itemId == android.R.id.home -> onBackPressed()
            item?.itemId == R.id.menu_favorites -> {
                favoriteAnimationView.playAnimation()
            }
        }
        return true
    }

    override fun onAnimationRepeat(animation: Animator?) {

    }

    override fun onAnimationEnd(animation: Animator?) {
        favoriteAnimationView.visibility = View.GONE
        favoriteIconImageView.setImageResource(R.drawable.icon_favorite_fill)
        favoriteIconImageView.visibility = View.VISIBLE
        binding.lottieAnimationView.visibility = View.GONE
        binding.fabFavorite.setImageResource(R.drawable.icon_favorite_fill)
    }

    override fun onAnimationCancel(animation: Animator?) {

    }

    override fun onAnimationStart(animation: Animator?) {
        favoriteAnimationView.visibility = View.VISIBLE
        favoriteIconImageView.visibility = View.GONE
    }
}