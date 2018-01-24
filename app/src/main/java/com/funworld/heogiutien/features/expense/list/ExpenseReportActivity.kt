package com.funworld.heogiutien.features.expense.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.CalendarView
import com.funworld.heogiutien.R
import kotlinx.android.synthetic.main.expense_activity.*
import kotlinx.android.synthetic.main.expense_sumup_layout.*
import java.util.*


class ExpenseReportActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expense_activity)

        initView()
        initViewAction()
    }

    private fun initView(){
        clv_month.date = Calendar.getInstance().timeInMillis
        clv_month.firstDayOfWeek = 2
        clv_month.setOnDateChangeListener() {view, year, month, dayofmonth -> {
            // check ngay su dung va hien thi thong tin ngay do ra
        }}
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