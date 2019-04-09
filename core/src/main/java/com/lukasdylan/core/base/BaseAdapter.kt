package com.lukasdylan.core.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<X, VH : BaseViewHolder<X>> : RecyclerView.Adapter<VH>() {

    private var data: List<X> = emptyList()
    private var imageTeamData: Map<String, String> = HashMap()
    private var usingMapOfImage = false

    abstract fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup) : ViewDataBinding

    abstract fun setViewHolder(binding: ViewDataBinding): VH

    fun addData(data: List<X>) {
        val diffResult = DiffUtil.calculateDiff(BaseItemDiffUtil(this.data, data))
        diffResult.dispatchUpdatesTo(this)
        this.data = data
    }

    fun addImageData(data: Map<String, String>) {
        usingMapOfImage = true
        imageTeamData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = getViewDataBinding(LayoutInflater.from(parent.context), parent)
        return setViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = data[position]
        if (usingMapOfImage) {
            holder.bindWithImageMap(item, imageTeamData)
        } else {
            holder.bind(item)
        }
    }
}