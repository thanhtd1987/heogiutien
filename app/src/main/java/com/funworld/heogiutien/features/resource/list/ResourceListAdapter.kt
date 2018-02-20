package com.funworld.heogiutien.features.resource.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.funworld.heogiutien.R
import com.funworld.heogiutien.common.utils.Utils
import kotlinx.android.synthetic.main.item_resouce_list.view.*
import com.funworld.heogiutien.common.inflate
import com.funworld.heogiutien.data.dao.Resource

class ResourceListAdapter(resources: MutableList<Resource>, private val listener: (Resource) -> Unit)
    : RecyclerView.Adapter<ResourceListAdapter.ResourceViewHolder>() {

    private var mResources: List<Resource>

    init {
        mResources = resources
    }

//    fun addResource(resource: Resource){
//        mResources.add(resource)
//        notifyDataSetChanged()
//    }

    override fun getItemCount(): Int {
        return mResources.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ResourceViewHolder(parent.inflate(R.layout.item_resouce_list))

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) = holder.bind(mResources[position], listener)

    class ResourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(resource: Resource, listener: (Resource) -> Unit) = with(itemView) {
            tv_resource_name.text = resource.name
            tv_resource_balance.text = String.format(context.getString(R.string.item_current_balance),
                    Utils.asMoneyAmount(resource.currentBalance, resource.currencyUnit))
            tv_open_balance.text = String.format(context.getString(R.string.item_open_balance),
                    Utils.asMoneyAmount(resource.openingBalance, resource.currencyUnit))
            tv_close_balance.text = String.format(context.getString(R.string.item_close_balance),
                    Utils.asMoneyAmount(resource.closingBalance, resource.currencyUnit))

            setOnClickListener { listener(resource) }
        }
    }
}