package com.funworld.heogiutien.common

import com.activeandroid.app.Application
import com.funworld.heogiutien.data.helper.ResourceHelper

/**
 * Created by ThanhTD on 6/25/2017.
 */

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        initResourceDB()
    }

    private fun initResourceDB(){
        ResourceHelper.addResource("CASH", "chi tieu tien mat")
//        ResourceHelper.addResource("CASH", "chi tieu tien mat")
    }
}
