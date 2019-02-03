package com.lukasdylan.football.ui.previousmatch

import android.animation.Animator
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.appbar.AppBarLayout
import com.lukasdylan.core.extension.*
import com.lukasdylan.core.widget.FootballPagerAdapter
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ActivityPreviousMatchBinding
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.androidx.viewmodel.ext.android.viewModel


class PreviousMatchActivity : AppCompatActivity() {

    private val favoriteIcon by lazy {
        ContextCompat.getDrawable(this, R.drawable.vector_favorite_fill)
    }

    private val notFavoriteIcon by lazy {
        ContextCompat.getDrawable(this, R.drawable.vector_favorite_no_fill_gray)
    }
    private val viewModel by viewModel<PreviousMatchViewModel>()

    private var favoriteAnimationView: LottieAnimationView? = null
    private var favoriteIconImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityPreviousMatchBinding>(this, R.layout.activity_previous_match)
        with(binding) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            initTextViewToolbar(toolbar = toolbar, appBarLayout = appBarLayout)
            lifecycleOwner = this@PreviousMatchActivity
            vm = viewModel
        }
        with(viewModel) {
            observeValue(matchName) { supportActionBar?.title = it }
            observeValue(matchInformationPage) { detailMatch ->
                val fragmentList = mutableListOf<Pair<String, Fragment>>()
                fragmentList.add(
                    Pair("Overview", MatchOverviewFragment().also {
                        it.arguments = bundleOf("detail_match" to detailMatch)
                    })
                )
                fragmentList.add(
                    Pair("${detailMatch.homeTeamName ?: '-'}", PlayerOverviewFragment().also {
                        it.arguments = bundleOf("detail_match" to detailMatch, "team_type" to 0)
                    })
                )
                fragmentList.add(
                    Pair("${detailMatch.awayTeamName ?: '-'}", PlayerOverviewFragment().also {
                        it.arguments = bundleOf("detail_match" to detailMatch, "team_type" to 1)
                    })
                )
                val pagerAdapter = FootballPagerAdapter(supportFragmentManager, fragmentList)
                binding.viewPager.adapter = pagerAdapter
                binding.viewPager.offscreenPageLimit = fragmentList.size
                binding.tabLayout.setupWithViewPager(binding.viewPager)
            }
            observeValue(isFavoriteMatch) {
                if (it) {
                    favoriteAnimationView?.playAnimation()
                } else {
                    favoriteIconImageView?.setImageDrawable(notFavoriteIcon)
                }
            }
            observeValue(errorSnackBarEvent) { binding.rootLayout.showErrorSnackBar(it.getMessage(this@PreviousMatchActivity))}
            observeValue(successSnackBarEvent) { binding.rootLayout.showSuccessSnackBar(it) }
            observeValue(normalSnackBarEvent) { binding.rootLayout.showNormalSnackBar(it) }
            intent?.extras?.let { return@with loadData(it) }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when {
            item?.itemId == android.R.id.home -> onBackPressed()
            item?.itemId == R.id.menu_favorites -> viewModel.onFavoriteIconClick()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_match, menu)
        val menuFavorite = menu?.findItem(R.id.menu_favorites)
        menuFavorite?.actionView?.apply {
            onClick {
                onOptionsItemSelected(menuFavorite)
            }
            favoriteAnimationView = find(R.id.lottieAnimationView)
            favoriteIconImageView = find(R.id.favoriteIcon)

            favoriteAnimationView?.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    favoriteAnimationView?.visibility = View.VISIBLE
                    favoriteIconImageView?.visibility = View.GONE
                }

                override fun onAnimationEnd(animation: Animator) {
                    favoriteAnimationView?.visibility = View.GONE
                    favoriteIconImageView?.setImageDrawable(favoriteIcon)
                    favoriteIconImageView?.visibility = View.VISIBLE
                }

                override fun onAnimationCancel(animation: Animator) {

                }

                override fun onAnimationRepeat(animation: Animator) {

                }
            })
        }
        viewModel.checkFavoriteMatch()
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