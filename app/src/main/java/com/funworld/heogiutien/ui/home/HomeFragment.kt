package com.funworld.heogiutien.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.funworld.heogiutien.R
import com.funworld.heogiutien.ui.main.ExpenseAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

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

        val adapter = ExpenseAdapter(context!!)
        rcv_latest_expense.adapter = adapter
        rcv_latest_expense.layoutManager = LinearLayoutManager(context)

        activity!!.let { it ->
            homeViewModel = ViewModelProviders.of(it).get(HomeViewModel::class.java)
            homeViewModel.expenses.observe(viewLifecycleOwner, Observer { expenses ->
                expenses.let { adapter.setExpenses(it) }
            })
            homeViewModel.resources.observe(viewLifecycleOwner, Observer { resources ->
                resources.let { adapter.setResourceNames(it) }
            })
            homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            text_home.text = it
            })
        }
    }
}
