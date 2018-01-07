package com.funworld.heogiutien.features.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.funworld.heogiutien.R
import com.funworld.heogiutien.features.expense.create.SumUpListExpenseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.top_layout.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setSupportActionBar(toolbar)
        intiViewActions()
    }

    private fun intiViewActions(){
        iv_expense.setOnClickListener({
            startActivity(Intent(this, SumUpListExpenseActivity::class.java))
        })

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    companion object {
        private val TAG = "MainActivity"
    }

}
