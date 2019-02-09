package com.lukasdylan.football.ui.playerlist.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukasdylan.core.widget.RoundedBottomSheetFragment
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.FragmentFilterPlayerBinding
import com.lukasdylan.football.ui.playerlist.PlayerListViewModel
import kotlinx.coroutines.delay
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FilterPlayerFragment : RoundedBottomSheetFragment() {

    private lateinit var binding: FragmentFilterPlayerBinding

    private val viewModel by sharedViewModel<PlayerListViewModel>()

    private var currentFilters = listOf<String>()

    private lateinit var filterPlayerAdapter: FilterPlayerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter_player, container, false)
        with(binding) {
            ivClose.onClick {
                delay(250)
                dismiss()
            }
            rvPositionList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
            }
            btnConfirm.onClick {
                dismiss()
                viewModel.onSelectedFilters(currentFilters)
            }
            btnReset.onClick {
                currentFilters = emptyList()
                filterPlayerAdapter.onReset()
            }
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            currentFilters = it.getStringArrayList("selected_filter").orEmpty()
            val positionMap = it.getSerializable("filter_map") as? List<Pair<String, Int>>

            filterPlayerAdapter = FilterPlayerAdapter(currentFilters.toMutableList()) { data ->
                currentFilters = data.first
                filterPlayerAdapter.notifyItemChanged(data.second)
            }
            filterPlayerAdapter.addData(positionMap.orEmpty())
            binding.rvPositionList.adapter = filterPlayerAdapter
        }
    }
}