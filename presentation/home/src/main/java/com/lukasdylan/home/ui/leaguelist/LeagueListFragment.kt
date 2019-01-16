package com.lukasdylan.home.ui.leaguelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukasdylan.core.widget.RoundedBottomSheetFragment
import com.lukasdylan.footballservice.data.model.League
import com.lukasdylan.home.R
import com.lukasdylan.home.databinding.FragmentLeagueListBinding
import com.lukasdylan.home.ui.home.HomeViewModel
import kotlinx.coroutines.delay
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LeagueListFragment : RoundedBottomSheetFragment() {

    private val homeViewModel by sharedViewModel<HomeViewModel>()
    private lateinit var binding: FragmentLeagueListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_league_list, container, false)
        with(binding) {
            ivClose.onClick {
                delay(250)
                dismiss()
            }
            rvLeagueList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val leagueList = League.leagueList
        val leagueListAdapter = LeagueListAdapter(leagueList) {
            dismiss()
            homeViewModel.onSelectedLeague(it)
        }
        binding.rvLeagueList.adapter = leagueListAdapter
    }
}