package com.funworld.heogiutien.app

import android.app.Application
import com.funworld.heogiutien.di.daoModule
import com.funworld.heogiutien.di.repositoryModule
import com.funworld.heogiutien.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


/**
 * Created by ThanhTD on 6/25/2017.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@App)
            modules(listOf(
                viewModelModule,
                repositoryModule,
                daoModule
            ))
        }
    }
}
