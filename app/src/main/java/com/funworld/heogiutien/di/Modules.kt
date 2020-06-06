package com.funworld.heogiutien.di

import com.funworld.heogiutien.data.local.LocalDatabase
import com.funworld.heogiutien.data.repository.ExpenseRepository
import com.funworld.heogiutien.ui.home.HomeViewModel
import com.funworld.heogiutien.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }

    viewModel { HomeViewModel() }
}

val repositoryModule = module {
    single {
        ExpenseRepository(get(), get())
    }
}

val daoModule = module {
//    factory { SupervisorJob() }
//    factory { CoroutineScope(Dispatchers.IO + get<SupervisorJob>()) }
    single { LocalDatabase.getDatabase(get()) }
    single { get<LocalDatabase>().expenseDao() }
    single { get<LocalDatabase>().resourceDao() }
}