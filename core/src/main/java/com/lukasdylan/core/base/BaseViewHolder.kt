package com.lukasdylan.core.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<X>(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    open fun bind(item: X) {}
    open fun bindWithImageMap(item: X, imageMap: Map<String, String>) {}
}
