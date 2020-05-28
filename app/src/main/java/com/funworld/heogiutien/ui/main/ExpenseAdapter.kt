package com.funworld.heogiutien.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.funworld.heogiutien.R
import com.funworld.heogiutien.model.entity.Expense
import com.funworld.heogiutien.model.entity.Resource

class ExpenseAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var expenses = emptyList<Expense>()
    private var resources = emptyList<Resource>()


    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val type: ImageView = itemView.findViewById(R.id.iv_row_ic_type)
        val amount: TextView = itemView.findViewById(R.id.tv_row_money_amount)
        val purpose: TextView = itemView.findViewById(R.id.tv_row_reason)
        val time: TextView = itemView.findViewById(R.id.tv_row_time)
        val resource: TextView = itemView.findViewById(R.id.tv_row_money_source)

        fun bind(expense: Expense) {
            val iconType = if (expense.type == "+") R.drawable.ic_add else R.drawable.ic_remove
//            val tint = if (expense.type == "+") R.color.colorAccent else R.color.colorPrimary
            type.setImageResource(iconType)
//            type.setColorFilter(tint)
            amount.text = expense.amount.toString() + itemView.context.getString(R.string.vnd_unit)
            purpose.text = expense.purpose
            time.text = expense.getShortCreatedTime()
            resource.text = resources.first { it.id == expense.resourceId }.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val itemView = inflater.inflate(R.layout.expense_row_layout, parent, false)
        return ExpenseViewHolder(itemView)
    }

    override fun getItemCount() = expenses.size

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.bind(expense)
    }

    internal fun setExpenses(expenses: List<Expense>) {
        this.expenses = expenses
        notifyDataSetChanged()
    }

    internal fun setResourceNames(resources: List<Resource>) {
        this.resources = resources
        notifyDataSetChanged()
    }
}