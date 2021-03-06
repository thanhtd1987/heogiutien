package com.funworld.heogiutien.data.repository

import androidx.lifecycle.LiveData
import com.funworld.heogiutien.data.local.dao.ExpenseDao
import com.funworld.heogiutien.data.local.dao.ResourceDao
import com.funworld.heogiutien.model.entity.Expense
import com.funworld.heogiutien.model.entity.Resource

class ExpenseRepository(private val expenseDao: ExpenseDao, private val resourceDao: ResourceDao) {

    val latestExpenses: LiveData<List<Expense>> = expenseDao.getLatestExpenses()

    val allResources: LiveData<List<Resource>> = resourceDao.getAllResource()

    suspend fun insertExpense(expense: Expense) {
        expenseDao.insert(expense)
        //update Resource with expense
        updateResourceByExpense(expense)
    }

    private suspend fun updateResourceByExpense(expense: Expense) {
        val resource = allResources.value?.first { it.id == expense.resourceId }
        val amount = if (expense.type == "-") expense.amount else -expense.amount
        resource!!.currentBalance -= amount
        resourceDao.update(resource!!)
    }

    suspend fun insertResource(resource: Resource) {
        resourceDao.insert(resource)
    }
}