package com.funworld.heogiutien.model

import com.funworld.heogiutien.common.base.adapter.ViewType
import com.funworld.heogiutien.common.constant.AdapterConst

class ResourceItem(
    val id: Int,
    val name: String,
    val description: String,
    val currentBalance: String,
    val openBalance: String
) : ViewType {
    override fun getViewType(): Int = AdapterConst.RESOURCE
}