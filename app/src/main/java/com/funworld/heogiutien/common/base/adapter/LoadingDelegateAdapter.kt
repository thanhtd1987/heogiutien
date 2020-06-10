package com.funworld.heogiutien.common.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.funworld.heogiutien.R
import com.funworld.heogiutien.utils.extention.inflate

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup) = LoadingViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class LoadingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.item_loading)
    )
}