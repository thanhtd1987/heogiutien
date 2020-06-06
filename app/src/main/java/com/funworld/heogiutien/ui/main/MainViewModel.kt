package com.funworld.heogiutien.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funworld.heogiutien.data.repository.ExpenseRepository
import com.funworld.heogiutien.model.entity.Expense
import com.funworld.heogiutien.model.entity.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ExpenseRepository) : ViewModel() {

    val resources: LiveData<List<Resource>> = repository.allResources
    val expenses: LiveData<List<Expense>> = repository.latestExpenses

    fun insert(expense: Expense) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertExpense(expense)
    }

    fun insert(resource: Resource) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertResource(resource)
    }
}