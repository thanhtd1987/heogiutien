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
import kotlinx.android.synthetic.main.activity_expense_report.*
import kotlinx.android.synthetic.main.expense_sumup_layout.*
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants

class ExpenseReportActivity : AppCompatActivity(), CalendarView.OnDateChangeListener {

    lateinit var mExpenses: List<Expense>
    private val mCurrentResource: Resource? by lazy { Resource.getResourceByName("CASH") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_report)

        initView()
        initViewAction()
    }

    private fun initView() {
        initListOfExpense()

        val dt = DateTime()
        clv_month.date = dt.toDate().time
        clv_month.firstDayOfWeek = 2
        clv_month.setOnDateChangeListener(this)
        clv_month.minDate = dt.dayOfMonth().withMinimumValue().millis
        clv_month.maxDate = dt.dayOfMonth().withMaximumValue().millis

        onSelectedDayChange(clv_month, dt.year, dt.monthOfYear - 1, dt.dayOfMonth)
    }

    private fun initViewAction() {
        iv_add_expense.setOnClickListener{CreateExpenseActivity.startActivity(this)}
        iv_back.setOnClickListener{onBackPressed()}
    }

    private fun initListOfExpense() {
        mExpenses = Expense.getByDate(mCurrentResource!!, DateTime.now().withTimeAtStartOfDay().millis)
        val adapter = ExpenseListAdapter(mExpenses, listener = {
            //TODO go to Expense 's detail
        })
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rcv_today_expenses.layoutManager = layoutManager
        rcv_today_expenses.adapter = adapter
    }

    override fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, dayOfMonth: Int) {
        if (year == 0) return
        //TODO update displaying info of date & sum of week
        val dt = DateTime(year, month + 1, dayOfMonth, 0, 0)
        var periodOfWeek = getSelectWeek(dt)
        tv_today.text = String.format(getString(R.string.expense_current_day), dt.dayOfWeek().asText, dayOfMonth, month + 1)
        tv_current_week.text = String.format(getString(R.string.expense_week_of_year), dt.weekOfWeekyear, periodOfWeek)

        //TODO update list of expense of day
        mExpenses = Expense.getByDate(mCurrentResource!!, dt.withTimeAtStartOfDay().millis)
        (rcv_today_expenses.adapter as ExpenseListAdapter).setExpenses(mExpenses)
//        val decoration = DividerItemDecoration(this, LinearLayout.HORIZONTAL)
//        rcv_today_expenses.addItemDecoration(decoration)
        tv_today_sum.text = asMoneyAmount(mExpenses.sumBy { it.amount })
        tv_week_expense_amount.text = asMoneyAmount(getSumOfWeek(dt))
    }

    private fun getDayInWeek(dt: DateTime): Pair<Long, Long> {
        val startOfWeek = dt.withDayOfWeek(DateTimeConstants.MONDAY).withTimeAtStartOfDay().millis
        val endOfWeek = dt.withDayOfWeek(DateTimeConstants.SUNDAY).withTimeAtStartOfDay().millis
        val startOfMonth = dt.withDayOfMonth(1).withTimeAtStartOfDay()

        val start = Math.max(startOfWeek, startOfMonth.millis)
        var end = Math.min(endOfWeek, startOfMonth.plusMonths(1).minusDays(1).millis)
        end = DateTime(end).withTime(23, 59, 59, 999).millis

//        Log.d("DEBUG", " start : ${DateTime(start).toString()} ")
//        Log.d("DEBUG", " end : ${DateTime(end).toString()} ")
        return Pair<Long, Long>(start, end)
    }

    private fun getSelectWeek(dt: DateTime): String {
        val pair = getDayInWeek(dt)
        return DateTime(pair.first).dayOfMonth.toString() + " - " + DateTime(pair.second).dayOfMonth
    }

    private fun getSumOfWeek(dt: DateTime): Int {
        val pair = getDayInWeek(dt)
        return Expense.getSumOfPeriod(mCurrentResource!!, pair.first, pair.second)
    }

    public fun asMoneyAmount(amount: Int) = amount.toString() + "k"

    companion object {
        private val INTENT_PARAM = "param"

        fun startActivity(context: Context) {
            val intent = Intent(context, ExpenseReportActivity::class.java)
            intent.putExtra(INTENT_PARAM, "hello")
            context.startActivity(intent)
        }
    }
}