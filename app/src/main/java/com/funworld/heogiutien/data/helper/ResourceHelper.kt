package com.funworld.heogiutien.data.helper

import com.funworld.heogiutien.data.dao.Expense
import com.funworld.heogiutien.data.dao.Resource
import org.joda.time.DateTime

class ResourceHelper {

    companion object {
        fun addResource(name: String, description: String, shortName: String = "", openingBalance: Int = 0
                        , currency: String = "", currencyUnit: String = ""): Resource? {
            var resource = Resource.getResourceByName(name)
            if (resource == null) {
                resource = Resource(name, description)
                val dt = DateTime()
                resource.openingDate = dt.millis
                resource.closingDate = dt.withDayOfMonth(1).plusMonths(1).minusDays(1)
                        .withTimeAtStartOfDay().withTime(23, 59, 59, 999).millis
                if (shortName != "")
                    resource.shortName = shortName
                if (openingBalance > 0) {
                    resource.openingBalance = openingBalance
                    resource.closingBalance = openingBalance
                    resource.currentBalance = openingBalance
                }

                if (currency != "")
                    resource.currency = currency
                if (currencyUnit != "")
                    resource.currencyUnit = currencyUnit

                resource.save()
                return resource
            } else
                return null
        }

        fun deleteByName(name: String) {
            var resource = Resource.getResourceByName(name)
            if (resource != null) {
                resource.delete()
            }
        }

        fun updateBalance(expense: Expense) {
            val resource = expense.resourceId
            if (expense.createdAt > resource.closingDate)
                openNewPeriod(resource, expense.createdAt)

            if (expense.type == Expense.WITHDRAW_TYPE) {
                resource.currentBalance -= expense.amount
            } else {
                resource.currentBalance += expense.amount
            }

            resource.closingBalance = resource.currentBalance

            resource.save()
        }

        fun openNewPeriod(resource: Resource, createdAt: Long) {
            val dt = DateTime(createdAt)
            resource.openingBalance = resource.currentBalance
            resource.openingDate = dt.withDayOfMonth(1).withTimeAtStartOfDay().millis
            resource.closingDate = dt.withDayOfMonth(1)
                    .plusMonths(1).minusDays(1).withTimeAtStartOfDay()
                    .withTime(23, 59, 59, 999).millis

            resource.save()
        }
    }
}