package com.funworld.heogiutien.features.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.funworld.heogiutien.R
import com.funworld.heogiutien.data.helper.ResourceHelper
import com.funworld.heogiutien.features.main.MainActivity
import org.joda.time.DateTime
import java.util.TimeZone

class SplashActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")) //set default time zone --> may be need to ask & set time zone in 1st time

        val currentTime = DateTime.now().millis
        val endTime = initResourceDB()
        var time = endTime - currentTime
        if(time > 1000) time = 1000

        Handler().postDelayed(Runnable {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1000 - time)
    }

    private fun initResourceDB(): Long{
//        ResourceHelper.deleteByName("CASH")
        ResourceHelper.addResource("CASH", "chi tieu tien mat")
//        ResourceHelper.addResource("CASH", "chi tieu tien mat")
        return DateTime.now().millis
    }
}