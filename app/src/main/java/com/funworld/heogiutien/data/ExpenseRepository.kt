package com.funworld.heogiutien.data

import androidx.lifecycle.LiveData
import com.funworld.heogiutien.data.dao.ExpenseDao
import com.funworld.heogiutien.data.dao.ResourceDao
import com.funworld.heogiutien.model.Expense
import com.funworld.heogiutien.model.Resource

class ExpenseRepository(private val expenseDao: ExpenseDao, private val resourceDao: ResourceDao) {

    val latestExpenses: LiveData<List<Expense>> = expenseDao.getLatestExpenses()

    val allResources: LiveData<List<Resource>> = resourceDao.getAllResource()

    suspend fun insertExpense(expense: Expense) {
        expenseDao.insert(expense)
    }

    suspend fun insertResource(resource: Resource) {
        resourceDao.insert(resource)
    }
}