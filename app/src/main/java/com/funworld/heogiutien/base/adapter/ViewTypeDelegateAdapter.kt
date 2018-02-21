package com.funworld.heogiutien.base.delegateAdapater

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by ThanhTD on 8/22/2017.
 */

interface ViewTypeDelegateAdapter {

    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)

    interface onViewSelectedListener {
        fun onItemSelected(item: ViewType?)
    }
}