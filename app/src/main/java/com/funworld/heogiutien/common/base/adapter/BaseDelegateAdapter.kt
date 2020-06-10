package com.funworld.heogiutien.common.base.adapter

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import com.funworld.heogiutien.common.constant.AdapterConst

open class BaseDelegateAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = mutableListOf<ViewType>()
    var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val loadingItem = object : ViewType {
        override fun getViewType() = AdapterConst.LOADING
    }

    init {
        delegateAdapters.put(AdapterConst.LOADING, LoadingDelegateAdapter())
        items.add(loadingItem)
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        delegateAdapters.get(viewType)!!.onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position))!!.onBindViewHolder(holder, items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getViewType()

    internal fun addItems(addItems: List<ViewType>) {
        // first remove loading and notify
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)

        items.addAll(addItems)
        items.add(loadingItem)
        notifyItemRangeChanged(initPosition, items.size + 1)
    }

    fun setItemList(setItems: List<ViewType>) {
        items.clear()

        items.addAll(setItems)
        items.add(loadingItem)
        notifyDataSetChanged()
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex
}