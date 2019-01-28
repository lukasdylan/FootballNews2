package com.lukasdylan.football.ui.team

import android.animation.Animator
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.appbar.AppBarLayout
import com.lukasdylan.core.extension.*
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ActivityDetailTeamBinding
import com.lukasdylan.football.ui.team.adapter.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.lukasdylan.newsservice.R as R2

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
                NAVIGATE_DETAIL_NEWS_SCREEN -> {
                    val scheme = resources.getString(R2.string.scheme)
                    val host = resources.getString(R2.string.deep_link_detail_news)
                    val bundle = bundleOf(*it.params.orEmpty())
                    openDeepLinkActivity(scheme, host, bundle)
                }
                NAVIGATE_ALL_NEWS_SCREEN -> {
                    viewModel.openListNewsScreen()
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
                viewModel.onFavoriteIconClick()
            }
        }
        with(viewModel) {
            observeValue(toolbarTitle) { supportActionBar?.title = it }
            observeValue(bannerList) { bannerAdapter.addData(it) }
            observeValue(detailTeam) { mainTeamInfoAdapter.updateTeamInfo(it) }
            observeValue(playerList) { mainTeamInfoAdapter.updateTeamPlayers(it) }
            observeValue(newsResponse) { mainTeamInfoAdapter.updateTeamNews(it) }
            observeValue(isFavoriteMatch) {
                if (it) {
                    if (binding.fabFavorite.isOrWillBeHidden) {
                        favoriteAnimationView.playAnimation()
                    } else {
                        binding.fabFavorite.setImageResource(0)
                        binding.lottieAnimationView.visibility = View.VISIBLE
                        binding.lottieAnimationView.playAnimation()
                    }
                } else {
                    favoriteIconImageView.setImageResource(R.drawable.icon_favorite_no_fill_gray)
                    binding.fabFavorite.setImageResource(R.drawable.icon_favorite_no_fill_gray)
                }
            }
            observeValue(navigationScreenEvent) {
                when (it.navigationId) {
                    NAVIGATE_ALL_NEWS_SCREEN -> {
                        val scheme = resources.getString(R2.string.scheme)
                        val host = resources.getString(R2.string.deep_link_list_news)
                        val bundle = bundleOf(*it.params.orEmpty())
                        openDeepLinkActivity(scheme, host, bundle)
                    }
                }
            }
            observeValue(errorSnackBarEvent) { binding.rootLayout.showErrorSnackBar(it.getMessage(this@DetailTeamActivity)) }
            observeValue(successSnackBarEvent) { binding.rootLayout.showSuccessSnackBar(it) }
            observeValue(normalSnackBarEvent) { binding.rootLayout.showNormalSnackBar(it) }
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
        viewModel.checkFavoriteTeam()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when {
            item?.itemId == android.R.id.home -> onBackPressed()
            item?.itemId == R.id.menu_favorites -> viewModel.onFavoriteIconClick()
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

    private fun initTextViewToolbar() {
        val toolbarTextView = binding.toolbar.titleTextView()
        toolbarTextView?.alpha = 0f

        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            val alpha =
                Math.abs(verticalOffset).toFloat() / (binding.appBarLayout.measuredHeight - binding.toolbar.measuredHeight)
            if (alpha <= 0.5f) {
                toolbarTextView?.alpha = alpha
            } else if (alpha <= 0.75) {
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
}