package com.funworld.heogiutien.features.expense.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.funworld.heogiutien.R
import com.funworld.heogiutien.data.model.Expense
import kotlinx.android.synthetic.main.expense_row_layout.view.*
import com.funworld.heogiutien.common.inflate
import com.funworld.heogiutien.common.utils.Utils

class ExpenseListAdapter(expenses: List<Expense>, val listener: (Expense) -> Unit)
    : RecyclerView.Adapter<ExpenseListAdapter.ExpenseViewHolder>() {

    private var mExpenses: List<Expense>

    init {
        mExpenses = expenses
    }

    fun setExpenses(expenses: List<Expense>){
        mExpenses = expenses
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mExpenses.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ExpenseViewHolder(parent.inflate(R.layout.expense_row_layout))

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int)
            = holder.bind(mExpenses[position], listener)

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(expense: Expense, listener: (Expense) -> Unit) = with(itemView) {
            tv_row_money_amount.text = Utils.asMoneyAmount(Math.abs(expense.amount), expense.resourceId.currencyUnit)
            tv_row_reason.text = expense.purpose
            tv_row_time.text = expense.getCreatedTime()
            if (expense.type == Expense.DEPOSIT_TYPE)
                iv_row_ic_type.setImageResource(R.drawable.ic_expense_in)
            else
                iv_row_ic_type.setImageResource(R.drawable.ic_expense_out)

            setOnClickListener { listener(expense) }
        }
    }
}