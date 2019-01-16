package com.lukasdylan.core.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<X, VH : BaseViewHolder<X>> : RecyclerView.Adapter<VH>() {

    private var data: List<X> = emptyList()
    private var imageTeamData: Map<String, String> = HashMap()

    abstract fun getViewDataBinding(inflater: LayoutInflater, parent: ViewGroup) : ViewDataBinding

    abstract fun setViewHolder(binding: ViewDataBinding): VH

    fun addData(data: List<X>) {
        this.data = data
    }

    fun addImageData(data: Map<String, String>) {
        imageTeamData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = getViewDataBinding(LayoutInflater.from(parent.context), parent)
        return setViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(data[position], imageTeamData)
    }
}