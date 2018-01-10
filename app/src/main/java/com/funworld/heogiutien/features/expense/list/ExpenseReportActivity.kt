package com.funworld.heogiutien.features.expense.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.funworld.heogiutien.R
import kotlinx.android.synthetic.main.expense_activity.*


class ExpenseReportActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expense_activity)

        initViewAction()
    }

    private fun initViewAction(){
        iv_add_expense.setOnClickListener({
            CreateExpenseActivity.startActivity(this)
        })
    }

    companion object {
        private val INTENT_PARAM = "param"

        fun startActivity(context: Context){
            val intent = Intent(context, ExpenseReportActivity::class.java)
            intent.putExtra(INTENT_PARAM, "hello")
            context.startActivity(intent)
        }
    }
}