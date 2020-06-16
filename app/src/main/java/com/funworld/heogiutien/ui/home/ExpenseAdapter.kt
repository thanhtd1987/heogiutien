package com.funworld.heogiutien.ui.home

import com.funworld.heogiutien.common.base.adapter.BaseDelegateAdapter
import com.funworld.heogiutien.common.base.adapter.ViewTypeDelegateAdapter
import com.funworld.heogiutien.common.constant.AdapterConst
import com.funworld.heogiutien.model.converter.ViewTypeConverters
import com.funworld.heogiutien.model.entity.Expense
import com.funworld.heogiutien.model.entity.Resource

class ExpenseAdapter internal constructor(viewAction: ViewTypeDelegateAdapter.ViewSelectedListener) :
    BaseDelegateAdapter() {

    init {
        delegateAdapters.put(AdapterConst.EXPENSE, ExpenseDelegateAdapter(viewAction))
    }

    fun addExpenses(expenses: List<Expense>, resources: List<Resource>) {
        addItems(expenses.map {
            ViewTypeConverters.toExpenseItem(it, resources)
        })
    }

    fun setExpenses(expenses: List<Expense>, resources: List<Resource>) {
        setItemList(expenses.map {
            ViewTypeConverters.toExpenseItem(it, resources)
        })
    }
}