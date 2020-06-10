package com.funworld.heogiutien.model.converter

import com.funworld.heogiutien.model.ExpenseItem
import com.funworld.heogiutien.model.entity.Expense
import com.funworld.heogiutien.model.entity.Resource

object ViewTypeConverters {

    fun toExpenseItem(expense: Expense, resources: List<Resource>) =
        ExpenseItem(
            expense.type,
            expense.amount,
            expense.purpose,
            expense.getShortCreatedTime(),
            resources.first { it.id == expense.resourceId }.name,
            expense.id
        )
}