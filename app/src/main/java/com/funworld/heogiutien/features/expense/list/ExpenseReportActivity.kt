package com.funworld.heogiutien.features.expense.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.CalendarView
import com.funworld.heogiutien.R
import com.funworld.heogiutien.data.dao.Expense
import com.funworld.heogiutien.data.dao.Resource
import com.funworld.heogiutien.features.expense.list.ExpenseListAdapter
import kotlinx.android.synthetic.main.expense_activity.*
import kotlinx.android.synthetic.main.expense_sumup_layout.*
import org.joda.time.DateTime
import java.util.*


class ExpenseReportActivity : AppCompatActivity(), CalendarView.OnDateChangeListener {

    lateinit var mExpenses: List<Expense>
    lateinit var mCurrentResource: Resource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expense_activity)

        initView()
        initViewAction()
    }

    private fun initView() {
        val dt = DateTime()
        clv_month.date = dt.toDate().time
        clv_month.firstDayOfWeek = 2
        clv_month.setOnDateChangeListener(this)

        initListOfExpense()
        onSelectedDayChange(clv_month, dt.year, dt.monthOfYear, dt.dayOfMonth)
    }

    private fun initViewAction() {
        iv_add_expense.setOnClickListener({
            CreateExpenseActivity.startActivity(this)
        })
    }

    private fun initListOfExpense(){
        mCurrentResource = Resource.getResourceByName("CASH")
        mExpenses = Expense.getExpenseByDate(mCurrentResource, DateTime().toDate().time)
        val adapter = ExpenseListAdapter(mExpenses, listener = {
            //TODO go to Expense 's detail
        })
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rcv_today_expenses.layoutManager = layoutManager
        rcv_today_expenses.adapter = adapter
    }

    override fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, dayOfMonth: Int) {
        if(year == 0) return
        //TODO update displaying info of date & sum of week
        val dt = DateTime(year,month, dayOfMonth, 0, 0)
        var periodOfWeek = getSelectWeek(dt)
        tv_today.text = String.format(getString(R.string.expense_current_day), dt.dayOfWeek().asShortText, dayOfMonth, month)
        tv_current_week.text = String.format(getString(R.string.expense_week_of_year), dt.weekyear, periodOfWeek)

        //TODO update list of expense of day
        mExpenses = Expense.getExpenseByDate(mCurrentResource, dt.toDate().time)
        rcv_today_expenses.adapter.notifyDataSetChanged()
    }

    private fun getSelectWeek(dt: DateTime): String{
        return dt.weekOfWeekyear().withMaximumValue().toString()
    }


    companion object {
        private val INTENT_PARAM = "param"

        fun startActivity(context: Context) {
            val intent = Intent(context, ExpenseReportActivity::class.java)
            intent.putExtra(INTENT_PARAM, "hello")
            context.startActivity(intent)
        }
    }
}