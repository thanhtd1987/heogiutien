package com.funworld.heogiutien.features.expense.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.funworld.heogiutien.R
import com.funworld.heogiutien.base.BaseActivity
import com.funworld.heogiutien.data.model.Expense
import kotlinx.android.synthetic.main.activity_expense_detail.*
import org.joda.time.format.DateTimeFormat

/**
 * Created by ThanhTD on 3/11/2018.
 */
class ExpenseDetailActivity: BaseActivity() {

    lateinit var expense: Expense

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_detail)

        expense = intent.getParcelableExtra<Expense>(PARAM_EXPENSE)
        if(expense != null){
            initView()
        }
    }

    override fun initView() {
        val dateFormat = DateTimeFormat.forPattern("Thứ E, DD-MM-yyyy HH:mm")
        tv_expense_time.text = dateFormat.print(expense.createdAt)
        et_expense_detail_purpose.setText(expense.purpose)
        et_expense_detail_amount.setText(expense.amount.toString() + " " + expense.resourceId.currencyUnit)
        tv_expense_detail_resource.text = expense.resourceId.name

    }

    override fun initViewAction() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    companion object {
        val PARAM_EXPENSE = "expense_param"
        fun startActivty(context: Context, expense: Expense){
            val intent = Intent(context, ExpenseDetailActivity::class.java)
            intent.putExtra(PARAM_EXPENSE, expense)
            context.startActivity(intent)
        }
    }
}