package com.funworld.heogiutien.base.delegateAdapater

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.funworld.heogiutien.R
import com.funworld.heogiutien.common.inflate

/**
 * Created by ThanhTD on 8/22/2017.
 */

class LoadingDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = LoadingViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class LoadingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_loading_delegate))
}