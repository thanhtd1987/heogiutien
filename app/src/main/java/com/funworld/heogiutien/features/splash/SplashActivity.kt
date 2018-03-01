package com.funworld.heogiutien.features.splash

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.funworld.heogiutien.R
import com.funworld.heogiutien.data.helper.ResourceHelper
import com.funworld.heogiutien.features.main.MainActivity
import org.joda.time.DateTime
import java.util.TimeZone

class SplashActivity : AppCompatActivity() {
    lateinit var startApp: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh")) //set default time zone --> may be need to ask & set time zone in 1st time

        startApp = Runnable {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        AsyncTaskInitResourceDB().execute()
    }

    private fun initResourceDB(): Long {
        ResourceHelper.addResource(getString(R.string.default_cash),
                getString(R.string.default_cash_des), getString(R.string.default_cash_short))
        return DateTime.now().millis
    }

    inner class AsyncTaskInitResourceDB : AsyncTask<String, Long, Long>() {
        private val start = DateTime.now().millis

        override fun doInBackground(vararg p0: String?): Long {
            return initResourceDB()
        }

        override fun onPostExecute(result: Long) {
            super.onPostExecute(result)
            val currentTime = result - start
            val stayTime = Math.max(0, 1000 - currentTime)
            Handler().postDelayed(startApp, stayTime)
        }
    }
}