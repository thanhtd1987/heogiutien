package com.funworld.heogiutien.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.funworld.heogiutien.R
import com.funworld.heogiutien.common.base.adapter.ViewTypeDelegateAdapter
import com.funworld.heogiutien.utils.extention.toast
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment(), ViewTypeDelegateAdapter.ViewSelectedListener {

    private val homeViewModel by sharedViewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {

        val adapter = ExpenseAdapter(this)
        rcv_latest_expense.adapter = adapter
        rcv_latest_expense.layoutManager = LinearLayoutManager(context)

        homeViewModel.expenses.observe(viewLifecycleOwner, Observer { expenses ->
            expenses.let { adapter.setExpenses(it, homeViewModel.resources.value!!) }
        })
    }

    override fun onItemSelected(expenseId: Int) {
        toast("Click on Expense Id: ${expenseId}")
    }
}
