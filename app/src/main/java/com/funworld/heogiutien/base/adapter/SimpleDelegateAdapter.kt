package com.funworld.heogiutien.base.delegateAdapater

/**
 * Created by ThanhTD on 9/11/2017.
 */

class SimpleDelegateAdapter(val type: Int, delegateAdapter: ViewTypeDelegateAdapter)
    : BaseDelegateAdapter() {

    override fun setupViewType(views: List<ViewType>): List<ViewType> {
        return views
    }

    init {
        delegateAdapters.put(type, delegateAdapter)
    }
}

//val adapter = SimpleDelegateAdapter(AdapterConstants.RAIL_LINE, RailLineDelegateAdapter(object : ViewTypeDelegateAdapter.onViewSelectedListener {
//    override fun onItemSelected(item: ViewType?) {
//        //do something
//    }
//}))