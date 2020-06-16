package com.funworld.heogiutien.ui.home

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.funworld.heogiutien.R
import com.funworld.heogiutien.common.base.adapter.ViewType
import com.funworld.heogiutien.common.base.adapter.ViewTypeDelegateAdapter
import com.funworld.heogiutien.model.ExpenseItem
import com.funworld.heogiutien.utils.extention.inflate
import kotlinx.android.synthetic.main.expense_row_layout.view.*

class ExpenseDelegateAdapter(val listener: ViewTypeDelegateAdapter.ViewSelectedListener) :
    ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = ExpenseViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as ExpenseViewHolder
        holder.bind(item as ExpenseItem)
    }

    inner class ExpenseViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.expense_row_layout)
    ) {
        private val type: ImageView = itemView.iv_row_ic_type
        private val amount: TextView = itemView.tv_row_money_amount
        private val purpose: TextView = itemView.tv_row_reason
        private val time: TextView = itemView.tv_row_time
        private val resource: TextView = itemView.tv_row_money_source

        fun bind(item: ExpenseItem) {
            val iconType = if (item.type == "+") R.drawable.ic_add else R.drawable.ic_remove
            type.setImageResource(iconType)
//            val tint = if (expense.type == "+") R.color.colorAccent else R.color.colorPrimary
//            type.setColorFilter(tint)
            amount.text = item.amount.toString() + itemView.context.getString(R.string.vnd_unit)
            purpose.text = item.purpose
            time.text = item.time
            resource.text = item.resourceName

            itemView.setOnClickListener { listener.onItemSelected(item.expenseId) }
        }
    }
}