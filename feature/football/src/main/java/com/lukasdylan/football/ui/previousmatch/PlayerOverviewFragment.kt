package com.lukasdylan.football.ui.previousmatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukasdylan.core.utility.asPlayerListFormat
import com.lukasdylan.football.R
import com.lukasdylan.footballservice.data.entity.DetailMatch
import com.lukasdylan.football.databinding.FragmentOverviewBinding

class PlayerOverviewFragment : Fragment() {

    private val playerOverviewAdapter by lazy {
        PlayerOverviewAdapter()
    }

    private lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overview, container, false)
        with(binding) {
            rvMatchInformation.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = playerOverviewAdapter
            }
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            val teamType = bundle.getInt("team_type", 0)
            val detailMatch = bundle.getParcelable<DetailMatch>("detail_match")
            return@let detailMatch?.let {
                val squadInformation = mutableListOf<String>()
                if (teamType == 0) {
                    binding.title = "${it.homeTeamName ?: '-'} Squad List"
                    squadInformation.add(it.homeGoalKeeper.asPlayerListFormat("Goalkeeper"))
                    squadInformation.add(it.homeDefenders.asPlayerListFormat("Defenders"))
                    squadInformation.add(it.homeMidfielders.asPlayerListFormat("Midfielders"))
                    squadInformation.add(it.homeForwarders.asPlayerListFormat("Forwarders"))
                } else {
                    binding.title = "${it.awayTeamName ?: '-'} Squad List"
                    squadInformation.add(it.awayGoalKeeper.asPlayerListFormat("Goalkeeper"))
                    squadInformation.add(it.awayDefenders.asPlayerListFormat("Defenders"))
                    squadInformation.add(it.awayMidfielders.asPlayerListFormat("Midfielders"))
                    squadInformation.add(it.awayForwarders.asPlayerListFormat("Forwarders"))
                }
                playerOverviewAdapter.addData(squadInformation)
                binding.executePendingBindings()
            }
        }
    }
}