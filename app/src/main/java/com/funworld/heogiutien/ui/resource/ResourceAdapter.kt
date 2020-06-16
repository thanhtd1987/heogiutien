package com.funworld.heogiutien.ui.resource

import com.funworld.heogiutien.common.base.adapter.BaseDelegateAdapter
import com.funworld.heogiutien.common.base.adapter.ViewTypeDelegateAdapter
import com.funworld.heogiutien.common.constant.AdapterConst
import com.funworld.heogiutien.model.converter.ViewTypeConverters
import com.funworld.heogiutien.model.entity.Resource

class ResourceAdapter internal constructor(viewAction: ViewTypeDelegateAdapter.ViewSelectedListener) :
    BaseDelegateAdapter() {

    init {
        delegateAdapters.put(AdapterConst.RESOURCE, ResourceDelegateAdapter(viewAction))
    }

    fun setResources(resources: List<Resource>) {
        setItemList(resources.map { ViewTypeConverters.toResourceItem(it) })
    }
}