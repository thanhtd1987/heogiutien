package com.funworld.heogiutien.data.helper

import com.funworld.heogiutien.data.model.Expense
import com.funworld.heogiutien.data.model.Resource

class ExpenseHelper {

    companion object {
        fun addExpense(purpose: String, amount: Int, resource: Resource,
                       isDeposit: Boolean, note: String, time: Long,
                       isLendMoney: Boolean? = null, relatedPerson: String = "", relatedAmount: Int = 0): String {
            val expense = Expense()
            expense.purpose = purpose
            expense.amount = amount
            expense.resourceId = resource
            if (isDeposit) {
                expense.type = Expense.DEPOSIT_TYPE
                expense.amount *= -1
            } else
                expense.type = Expense.WITHDRAW_TYPE

            expense.createdAt = time
            expense.updatedAt = expense.createdAt
            expense.note = note
            expense.save()

            if (isLendMoney != null) {
                DebtHelper.addDebt(purpose, relatedAmount, isLendMoney, relatedPerson, resource, expense)
            }

            ResourceHelper.updateBalance(expense)

            return expense.id.toString()
        }

        fun transferMoney(purpose: String, amount: Int, from: Resource, to: Resource, note: String, time: Long): String{
            val fromExpenseId = addExpense(purpose, amount, from, false, note, time)

            val toExpenseId = addExpense(purpose, amount, to, true, note, time)

            return "Transfer from expense $fromExpenseId -> $toExpenseId"
        }
    }
}