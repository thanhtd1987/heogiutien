package com.funworld.heogiutien.features.expense.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.funworld.heogiutien.R


class CreateExpenseActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_expense_activity)

        val param = intent.getStringExtra(INTENT_PARAM) ?: "" //throw Exception()
    }

    companion object {
        private val INTENT_PARAM = "param"
        fun newIntent(context: Context): Intent{
            val intent = Intent(context, CreateExpenseActivity::class.java)
            intent.putExtra(INTENT_PARAM, "hello")
            return intent
        }
    }
}