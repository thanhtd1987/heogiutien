package com.funworld.heogiutien.model

import com.funworld.heogiutien.common.base.adapter.ViewType
import com.funworld.heogiutien.common.constant.AdapterConst

class ExpenseItem(
    val type: String,
    val amount: Int,
    val purpose: String,
    val time: String,
    val resourceName: String,
    val expenseId: Int
) : ViewType {
    override fun getViewType() = AdapterConst.EXPENSE
}