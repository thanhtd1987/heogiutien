package com.funworld.heogiutien.features.expense.create

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.funworld.heogiutien.R
import com.funworld.heogiutien.common.inflate
import com.funworld.heogiutien.data.dao.Resource
import kotlinx.android.synthetic.main.item_dialog_resource_list.view.*

class ResourceListAdapter(val mResource: List<Resource>, val listener: (Resource) -> Unit)
    : RecyclerView.Adapter<ResourceListAdapter.ResourceViewHolder>() {

    var mSelectedResource: Resource? = null

    override fun getItemCount(): Int {
        return mResource.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ResourceViewHolder(parent.inflate(R.layout.item_dialog_resource_list))

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int)
            = holder.bind(mSelectedResource, mResource[position], listener)

    class ResourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(currentResource: Resource?, resource: Resource, listener: (Resource) -> Unit) = with(itemView) {
            tv_item_resource_name.text = resource.name
            if (currentResource != null && currentResource.name == resource.name )
                iv_item_resource_check.visibility = View.VISIBLE
            else
                iv_item_resource_check.visibility = View.GONE

            setOnClickListener { listener(resource) }
        }
    }
}