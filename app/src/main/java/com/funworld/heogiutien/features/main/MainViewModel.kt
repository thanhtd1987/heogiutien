package com.funworld.heogiutien.features.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.funworld.heogiutien.data.ExpenseRepository
import com.funworld.heogiutien.data.LocalDatabase
import com.funworld.heogiutien.model.Expense
import com.funworld.heogiutien.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExpenseRepository

    val resources: LiveData<List<Resource>>
    val expenses: LiveData<List<Expense>>

    init {
        val expenseDao = LocalDatabase.getDatabase(application, viewModelScope).expenseDao()
        val resourceDao = LocalDatabase.getDatabase(application, viewModelScope).resourceDao()
        repository = ExpenseRepository(expenseDao, resourceDao)
        resources = repository.allResources
        expenses = repository.latestExpenses
    }

    fun insert(expense: Expense) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertExpense(expense)
    }

    fun insert(resource: Resource) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertResource(resource)
    }
}