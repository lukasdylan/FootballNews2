package com.lukasdylan.football.ui.playerlist.filter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.lukasdylan.core.base.BaseAdapter
import com.lukasdylan.core.base.BaseViewHolder
import com.lukasdylan.football.R
import com.lukasdylan.football.databinding.ItemFilterPositionBinding
import org.jetbrains.anko.sdk27.coroutines.onClick


class FilterPlayerAdapter(
    private val selectedFilterPosition: MutableList<String>,
    private val listener: (Pair<List<String>, Int>) -> Unit
) : BaseAdapter<Pair<String, Int>, FilterPlayerAdapter.PositionViewHolder>() {

    override fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate<ItemFilterPositionBinding>(
            inflater,
            R.layout.item_filter_position,
            parent,
            false
        )
    }

    override fun setViewHolder(binding: ViewDataBinding): PositionViewHolder {
        return PositionViewHolder(binding as ItemFilterPositionBinding, listener)
    }

    fun onReset() {
        selectedFilterPosition.clear()
        notifyDataSetChanged()
    }

    inner class PositionViewHolder(
        private val binding: ItemFilterPositionBinding,
        private val listener: (Pair<List<String>, Int>) -> Unit
    ) : BaseViewHolder<Pair<String, Int>>(binding) {

        @SuppressLint("SetTextI18n")
        override fun bind(item: Pair<String, Int>) {
            with(binding) {
                val isFilterPosition = if (selectedFilterPosition.isEmpty()) {
                    false
                } else {
                    !selectedFilterPosition.find { it == item.first }.isNullOrBlank()
                }
                cbPosition.isChecked = isFilterPosition
                cbPosition.text = "${item.first} (${item.second})"
                cbPosition.onClick {
                    if (!isFilterPosition) {
                        cbPosition.isChecked = true
                        selectedFilterPosition.add(item.first)
                    } else {
                        cbPosition.isChecked = false
                        selectedFilterPosition.remove(item.first)
                    }
                    listener(Pair(selectedFilterPosition, adapterPosition))
                }
                executePendingBindings()
            }
        }
    }
}