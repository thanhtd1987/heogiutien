package com.funworld.heogiutien.features.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.funworld.heogiutien.R
import com.funworld.heogiutien.features.expense.create.CreateExpenseActivity
import com.funworld.heogiutien.features.expense.create.ExpenseReportActivity
import com.funworld.heogiutien.features.resource.ResourcesActivity
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
        iv_expense_report.setOnClickListener({
            ExpenseReportActivity.startActivity(this)
        })

        iv_account.setOnClickListener{ ResourcesActivity.startActivity(this)}

        iv_statistic.setOnClickListener{}

        iv_saving.setOnClickListener{}

        fab.setOnClickListener { view ->
                CreateExpenseActivity.startActivity(this)
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
        }
    }

    companion object {
        private val TAG = "MainActivity"
    }

}
