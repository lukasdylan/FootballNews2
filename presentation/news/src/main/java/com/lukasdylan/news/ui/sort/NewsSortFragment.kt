package com.lukasdylan.news.ui.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukasdylan.core.widget.RoundedBottomSheetFragment
import com.lukasdylan.news.R
import com.lukasdylan.news.databinding.FragmentNewsSortBinding
import com.lukasdylan.news.ui.listnews.ListNewsViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NewsSortFragment : RoundedBottomSheetFragment() {

    private val listNewsViewModel by sharedViewModel<ListNewsViewModel>()
    private lateinit var binding: FragmentNewsSortBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_sort, container, false)
        with(binding) {
            rvSortList.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
            }
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectedSort = arguments?.getString("sort_name").orEmpty()
        val newsSortAdapter = NewsSortAdapter(selectedSort) {
            dismiss()
            listNewsViewModel.onSelectedSort(it)
        }
        newsSortAdapter.addData(listOf("Published Time", "Popularity", "Relevancy"))
        binding.rvSortList.adapter = newsSortAdapter
    }
}