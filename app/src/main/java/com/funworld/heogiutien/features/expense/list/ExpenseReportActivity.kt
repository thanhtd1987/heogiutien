package com.funworld.heogiutien.features.expense.create

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.CalendarView
import com.funworld.heogiutien.R
import com.funworld.heogiutien.base.BaseActivity
import com.funworld.heogiutien.common.utils.Utils
import com.funworld.heogiutien.data.dao.Expense
import com.funworld.heogiutien.data.dao.Resource
import com.funworld.heogiutien.features.expense.list.ExpenseListAdapter
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import kotlinx.android.synthetic.main.activity_expense_report.*
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants

class ExpenseReportActivity : BaseActivity(), CalendarView.OnDateChangeListener {

    lateinit var mExpenses: List<Expense>
    private val mCurrentResource: Resource? by lazy { Resource.getResourceByName(getString(R.string.default_cash)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_report)

        initView()
        initViewAction()
    }

    override fun initView() {
        super.initView()

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
//        main_collapsing.expandedTitleGravity = Gravity.CENTER or Gravity.BOTTOM
//        main_collapsing.expandedTitleMarginStart = resources.getDimensionPixelSize(R.dimen.gap_normal)
        main_collapsing.expandedTitleMarginBottom = resources.getDimensionPixelSize(R.dimen.gap_small)

        initListOfExpense()

        initCalendarView()


    }

    override fun initViewAction() {
        super.initViewAction()

//        iv_add_expense.setOnClickListener{CreateExpenseActivity.startActivity(this)}
    }

    private fun initCalendarView() {
        val dt = DateTime()
        //material calendar
        clv_month.selectedDate = CalendarDay.from(dt.toDate())
        clv_month.currentDate = CalendarDay.from(dt.toDate())
        clv_month.leftArrowMask = null
        clv_month.rightArrowMask = null
        clv_month.setOnDateChangedListener(OnDateSelectedListener(function = { widget, date, selected ->
            onSelectedDayChange(null, date.year, date.month, date.day)
        }))
        clv_month.selectionColor = Color.RED
        clv_month.setBackgroundColor(Color.GRAY)

        // default calendar
//        clv_month11.date = dt.toDate().time
//        clv_month11.firstDayOfWeek = 2
//        clv_month11.setOnDateChangeListener(this)
//        clv_month11.minDate = dt.dayOfMonth().withMinimumValue().millis
//        clv_month11.maxDate = dt.dayOfMonth().withMaximumValue().millis
//
        onSelectedDayChange(null, dt.year, dt.monthOfYear - 1, dt.dayOfMonth)
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
        val decoration = DividerItemDecoration(this, layoutManager.orientation)
        rcv_today_expenses.addItemDecoration(decoration)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onSelectedDayChange(view: CalendarView?, year: Int, month: Int, dayOfMonth: Int) {
        if (year == 0) return
        val dt = DateTime(year, month + 1, dayOfMonth, 0, 0)
        var periodOfWeek = getSelectWeek(dt)
//        tv_today.text = String.format(getString(R.string.expense_current_day), dt.dayOfWeek().asText, dayOfMonth, month + 1)
        tv_current_week.text = String.format(getString(R.string.expense_week_of_year), dt.weekOfWeekyear, periodOfWeek)

        mExpenses = Expense.getByDate(mCurrentResource!!, dt.withTimeAtStartOfDay().millis)
        (rcv_today_expenses.adapter as ExpenseListAdapter).setExpenses(mExpenses)

        main_collapsing.title = String.format(getString(R.string.expense_current_day), dt.dayOfWeek().asShortText, dayOfMonth, month + 1) + " " + Utils.asMoneyAmount(mExpenses.sumBy { it.amount }, mCurrentResource!!.currencyUnit)

//        tv_today_sum.text = Utils.asMoneyAmount(mExpenses.sumBy { it.amount }, mCurrentResource!!.currencyUnit)
        tv_week_expense_amount.text = Utils.asMoneyAmount(getSumOfWeek(dt), mCurrentResource!!.currencyUnit)
    }

    private fun getDayInWeek(dt: DateTime): Pair<Long, Long> {
        val startOfWeek = dt.withDayOfWeek(DateTimeConstants.MONDAY).withTimeAtStartOfDay().millis
        val endOfWeek = dt.withDayOfWeek(DateTimeConstants.SUNDAY).withTimeAtStartOfDay().millis
        val startOfMonth = dt.withDayOfMonth(1).withTimeAtStartOfDay()

        val start = Math.max(startOfWeek, startOfMonth.millis)
        var end = Math.min(endOfWeek, startOfMonth.plusMonths(1).minusDays(1).millis)
        end = DateTime(end).withTime(23, 59, 59, 999).millis

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

    companion object {
        private const val INTENT_PARAM = "param"

        fun startActivity(context: Context) {
            val intent = Intent(context, ExpenseReportActivity::class.java)
            intent.putExtra(INTENT_PARAM, "hello")
            context.startActivity(intent)
        }
    }
}