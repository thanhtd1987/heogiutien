package com.funworld.heogiutien.common

import android.app.Application
import com.activeandroid.ActiveAndroid
import com.activeandroid.Configuration
import com.funworld.heogiutien.data.dao.Debt
import com.funworld.heogiutien.data.dao.Expense
import com.funworld.heogiutien.data.dao.Resource

/**
 * Created by ThanhTD on 6/25/2017.
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val dbConfig = Configuration.Builder(this)
                .setDatabaseName("heogiutienDB")
                .setDatabaseVersion(6)
                .addModelClass(Resource::class.java)
                .addModelClass(Expense::class.java)
                .addModelClass(Debt::class.java)
                .create()
        ActiveAndroid.initialize(dbConfig)
    }
}
