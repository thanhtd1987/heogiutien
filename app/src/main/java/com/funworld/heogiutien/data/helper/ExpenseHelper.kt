package com.funworld.heogiutien.data.helper

import com.funworld.heogiutien.data.dao.Expense
import com.funworld.heogiutien.data.dao.Resource

class ExpenseHelper{

    companion object {
        fun addExpense(purpose: String, amount: Int, resource: Resource, relatedType: String,
                            relatedPerson: String, note: String): Expense{
            val expense = Expense()
            expense.purpose = purpose
            expense.amount = amount
            expense.resourceId = resource
            expense.relatedType = relatedType
            expense.relatedPerson = relatedPerson
            expense.note = note
            expense.save()
            return expense
        }
    }
}