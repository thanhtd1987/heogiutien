package com.funworld.heogiutien.data.helper

import com.funworld.heogiutien.data.dao.Debt
import com.funworld.heogiutien.data.dao.Expense
import com.funworld.heogiutien.data.dao.Resource
import org.joda.time.DateTime

class DebtHelper {

    companion object {
        fun addDebt(purpose: String, amount: Int, isDebt: Boolean,
                    relatedPerson: String, resource: Resource, expense: Expense): Debt {
            val debt = Debt()
            debt.purpose = purpose
            debt.amount = amount
            debt.resourceId = resource
            debt.expenseId = expense
            if (isDebt)
                debt.type = Debt.DEBT_TYPE
            else
                debt.type = Debt.BORROW_TYPE
            debt.person = relatedPerson
            debt.createdAt = DateTime.now().millis
            debt.updatedAt = debt.createdAt

            debt.save()
            return debt
        }
    }
}