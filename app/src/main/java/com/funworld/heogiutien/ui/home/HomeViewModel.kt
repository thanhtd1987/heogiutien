package com.funworld.heogiutien.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.funworld.heogiutien.model.entity.Expense
import com.funworld.heogiutien.model.entity.Resource

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val expenses = MutableLiveData<List<Expense>>()

    val resources = MutableLiveData<List<Resource>>()

    val addedExpense = MutableLiveData<Expense>()

    val addedResource = MutableLiveData<Resource>()

    val text: LiveData<String> = _text

}