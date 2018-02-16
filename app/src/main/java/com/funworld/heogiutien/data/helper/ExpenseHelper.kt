package com.funworld.heogiutien.data.helper

import com.funworld.heogiutien.data.dao.Expense
import com.funworld.heogiutien.data.dao.Resource
import org.joda.time.DateTime

class ExpenseHelper {

    companion object {
        fun addExpense(purpose: String, amount: Int, resource: Resource,
                       isDeposit: Boolean, note: String,
                       isDebt: Boolean?, relatedPerson: String): Expense {
            val expense = Expense()
            expense.purpose = purpose
            expense.amount = amount
            expense.resourceId = resource
            if (isDeposit)
                expense.type = Expense.DEPOSIT_TYPE
            else
                expense.type = Expense.WITHDRAW_TYPE
            expense.createdAt = DateTime.now().millis
            expense.updatedAt = expense.createdAt
            expense.note = note
            expense.save()

            if (isDebt != null) {
                DebtHelper.addDebt(purpose, amount, isDebt, relatedPerson, resource, expense)
            }

            ResourceHelper.updateBalance(expense)

            return expense
        }
    }
}