package com.lukasdylan.football.ui.previousmatch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukasdylan.core.utility.asGoalScorerFormat
import com.lukasdylan.core.utility.asStringDate
import com.lukasdylan.football.R
import com.lukasdylan.footballservice.data.entity.DetailMatch
import com.lukasdylan.football.databinding.FragmentOverviewBinding
import java.util.*

class MatchOverviewFragment : Fragment() {

    private val matchOverviewAdapter by lazy {
        MatchOverviewAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentOverviewBinding>(
            inflater,
            R.layout.fragment_overview,
            container,
            false
        )
        with(binding) {
            title = "Match Information"
            rvMatchInformation.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = matchOverviewAdapter
            }
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            val detailMatch = bundle.getParcelable<DetailMatch>("detail_match")
            return@let detailMatch?.let {
                val matchInformation = mutableListOf<Pair<String, String>>()
                matchInformation.add(Pair("Match Name", it.matchName ?: "-"))
                matchInformation.add(Pair("League Name", it.leagueName ?: "-"))
                matchInformation.add(Pair("Match Date", Calendar.getInstance().asStringDate(it.date.orEmpty(), it.time.orEmpty())))
                matchInformation.add(
                    Pair("${it.homeTeamName ?: '-'} Goals", it.homeGoalDetails.asGoalScorerFormat())
                )
                matchInformation.add(
                    Pair("${it.awayTeamName ?: '-'} Goals", it.awayGoalDetails.asGoalScorerFormat())
                )
                matchInformation.add(
                    Pair("${it.homeTeamName ?: '-'} Yellow Cards", it.homeYellowCards.asGoalScorerFormat())
                )
                matchInformation.add(
                    Pair("${it.awayTeamName ?: '-'} Yellow Cards", it.awayYellowCards.asGoalScorerFormat())
                )
                matchInformation.add(
                    Pair("${it.homeTeamName ?: '-'} Red Cards", it.homeRedCards.asGoalScorerFormat())
                )
                matchInformation.add(
                    Pair("${it.awayTeamName ?: '-'} Red Cards", it.awayRedCards.asGoalScorerFormat())
                )
                matchOverviewAdapter.addData(matchInformation)
            }
        }
    }
}