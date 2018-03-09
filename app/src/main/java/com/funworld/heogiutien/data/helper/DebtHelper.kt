package com.funworld.heogiutien.data.helper

import com.funworld.heogiutien.data.model.Debt
import com.funworld.heogiutien.data.model.Expense
import com.funworld.heogiutien.data.model.Resource
import org.joda.time.DateTime

class DebtHelper {

    companion object {
        fun addDebt(purpose: String, amount: Int, isLendMoney: Boolean,
                    relatedPerson: String, resource: Resource, expense: Expense): Debt {
            val debt = Debt()
            debt.purpose = purpose
            debt.amount = amount
            debt.resourceId = resource
            debt.expenseId = expense
            if (isLendMoney)
                debt.type = Debt.TYPE_LEND
            else
                debt.type = Debt.TYPE_BORROW
            debt.person = relatedPerson
            debt.createdAt = DateTime.now().millis
            debt.updatedAt = debt.createdAt

            debt.save()
            return debt
        }
    }
}