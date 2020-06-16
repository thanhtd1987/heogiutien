package com.funworld.heogiutien.ui.resource

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.funworld.heogiutien.R
import com.funworld.heogiutien.common.base.adapter.ViewType
import com.funworld.heogiutien.common.base.adapter.ViewTypeDelegateAdapter
import com.funworld.heogiutien.model.ResourceItem
import com.funworld.heogiutien.utils.extention.inflate
import kotlinx.android.synthetic.main.item_resource.view.*

class ResourceDelegateAdapter(val listener: ViewTypeDelegateAdapter.ViewSelectedListener) :
    ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = ResourceViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as ResourceViewHolder
        holder.bind(item as ResourceItem)
    }

    inner class ResourceViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.item_resource)
    ) {
        val name: TextView = itemView.tv_resource_name
        private val description: TextView = itemView.tv_resource_desc
        private val currentBalance = itemView.tv_current_balance
        private val openBalance = itemView.tv_open_balance

        fun bind(resourceItem: ResourceItem) {
            name.text = resourceItem.name
            description.text = resourceItem.description
            currentBalance.text = String.format(
                itemView.context.getString(R.string.item_resource_current_balance),
                resourceItem.currentBalance
            )
            openBalance.text = String.format(
                itemView.context.getString(R.string.item_resource_open_balance),
                resourceItem.openBalance
            )

            itemView.setOnClickListener { v -> listener.onItemSelected(resourceItem.id) }
        }
    }
}