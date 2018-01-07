package com.funworld.heogiutien.features.expense.create

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.funworld.heogiutien.R
import kotlinx.android.synthetic.main.expense_activity.*


class SumUpListExpenseActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expense_activity)

        initViewAction()
    }

    private fun initViewAction(){
        iv_add_expense.setOnClickListener({
            startActivity(Intent(this, CreateExpenseActivity::class.java))
        })
    }
}