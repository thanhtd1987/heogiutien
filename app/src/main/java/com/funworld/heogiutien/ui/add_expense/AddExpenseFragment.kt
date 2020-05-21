package com.funworld.heogiutien.ui.add_expense

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.funworld.heogiutien.R
import com.funworld.heogiutien.common.*
import com.funworld.heogiutien.model.Expense
import com.funworld.heogiutien.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.add_expense_fragment.*
import java.time.LocalDateTime
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AddExpenseFragment : Fragment(),
    DatePickerDialog.OnDateSetListener {

    private lateinit var listOfResourceId: IntArray
    private lateinit var listOfResourceName: Array<String>
    private lateinit var createdDateTime: LocalDateTime
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.add_expense_fragment, container, false)
        setHasOptionsMenu(true)
        listOfResourceId = arguments?.getIntArray("resource_ids")!!
        listOfResourceName = arguments?.getStringArray("resource_names")!!
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initResource()
        initCreatedTime()
        adding_layout.setOnClickListener { hideKeyboard() }

        activity!!.let {
            homeViewModel = ViewModelProviders.of(it).get(HomeViewModel::class.java)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.add_expense_app_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                toast("Save expense")
                if (!isInvalidateInput()) {
                    onSaved()
                    hideKeyboard()
                    parentFragmentManager.popBackStack()
                    true
                } else {
                    toast("Invalid input")
                }
            }
            android.R.id.home -> {
                hideKeyboard()
                true
            }
            else -> toast("other items " + item.title)
        }
        return item.onNavDestinationSelected(findNavController()) || super.onOptionsItemSelected(
            item
        )
    }

    private fun initResource() {
        val adapter =
            ArrayAdapter(
                context!!,
                android.R.layout.simple_spinner_dropdown_item,
                listOfResourceName
            )
        spinner_resource.adapter = adapter
    }

    private fun initCreatedTime() {
        createdDateTime = LocalDateTime.now()
        setCreatedDate(createdDateTime.getDateAsString())
        setCreatedTime(createdDateTime.getTimeWithoutSeconds())
        tv_created_date.setOnClickListener {
            DatePickerDialog(
                context!!,
                this,
                createdDateTime.year,
                createdDateTime.monthValue - 1,
                createdDateTime.dayOfMonth
            ).show()
        }
        tv_created_time.setOnClickCoroutine(this) {
            createdDateTime = pickTime(createdDateTime)
            setCreatedTime(createdDateTime.getTimeWithoutSeconds())
        }
    }

    private fun isInvalidateInput() =
        edt_money_amount.text!!.isEmpty() || edt_purpose.text!!.isEmpty()

    private fun getCreatedTime(): LocalDateTime {
        return createdDateTime
    }

    private fun getAddedExpense() = Expense(
        if (rgr_type.checkedRadioButtonId == R.id.rdb_expense) "-" else "+",
        edt_money_amount.text.toString().toInt(),
        edt_purpose.text.toString(),
        listOfResourceId[spinner_resource.selectedItemPosition],
        getCreatedTime()
    )

    private fun setCreatedDate(date: String) {
        tv_created_date.text = date
    }

    private fun setCreatedTime(time: String) {
        tv_created_time.text = time
    }

    suspend fun pickTime(init: LocalDateTime): LocalDateTime =
        suspendCoroutine { continuation ->
            TimePickerDialog(
                context!!,
                TimePickerDialog.OnTimeSetListener { _, hour, min ->
                    // Pass dialog result to continuation
                    continuation.resume(createdDateTime.withHour(hour).withMinute(min))
                },
                init.hour, init.minute, true
            ).show()
        }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
        createdDateTime = createdDateTime.withYear(year).withMonth(month + 1).withDayOfMonth(day)
        setCreatedDate(createdDateTime.getDateAsString())
    }

    private fun onSaved() {
        homeViewModel.addedExpense.postValue(getAddedExpense())
    }
}

