package com.funworld.heogiutien.ui.add_expense

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.funworld.heogiutien.R
import com.funworld.heogiutien.model.entity.Expense
import com.funworld.heogiutien.ui.home.HomeViewModel
import com.funworld.heogiutien.utils.extention.*
import kotlinx.android.synthetic.main.add_expense_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.time.LocalDateTime
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AddExpenseFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var listOfResourceId: IntArray
    private lateinit var listOfResourceName: Array<String>
    private lateinit var createdDateTime: LocalDateTime
    private val homeViewModel by sharedViewModel<HomeViewModel>()

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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.add_expense_app_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                if (!isValidTransfer()) {
                    toast("Received resource & Send resource must be different")
                } else if (isInvalidateInput()) {
                    toast("Invalid input")
                } else {
                    toast("Save expense")
                    onSaved()
                    hideKeyboard()
                    findNavController().popBackStack()
                    true
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

        rgr_type.setOnCheckedChangeListener { rgr, id ->
            run {
                when (id) {
                    R.id.rdb_transfer -> {
                        spinner_transfer_to_resource.visibility = View.VISIBLE
                        tv_label_received.visibility = View.VISIBLE
                        val text = getString(R.string.transfer) + " " + edt_purpose.text.toString().trim()
                        edt_purpose.setText(text)
                    }
                    else -> {
                        spinner_transfer_to_resource.visibility = View.GONE
                        tv_label_received.visibility = View.GONE
                        val text = edt_purpose.text.toString()
                        edt_purpose.setText(text.substringAfter(getString(R.string.transfer)))
                    }
                }
            }
        }

        spinner_transfer_to_resource.adapter = adapter
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

    private fun isValidTransfer() = rgr_type.checkedRadioButtonId != R.id.rdb_transfer ||
            (rgr_type.checkedRadioButtonId == R.id.rdb_transfer &&
                    spinner_resource.selectedItemPosition != spinner_transfer_to_resource.selectedItemPosition)

    private fun getCreatedTime(): LocalDateTime {
        return createdDateTime
    }

    private fun getAddedExpense() = Expense(
        if (rgr_type.checkedRadioButtonId == R.id.rdb_expense ||
            rgr_type.checkedRadioButtonId == R.id.rdb_transfer
        ) "-" else "+",
        edt_money_amount.text.toString().toInt(),
        edt_purpose.text.toString(),
        listOfResourceId[spinner_resource.selectedItemPosition],
        getCreatedTime()
    )

    private fun getTransferExpense() = Expense(
        "+",
        edt_money_amount.text.toString().toInt(),
        edt_purpose.text.toString(),
        listOfResourceId[spinner_transfer_to_resource.selectedItemPosition],
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
        if (rgr_type.checkedRadioButtonId == R.id.rdb_transfer)
            CoroutineScope(Dispatchers.IO).launch {
                val expense = getTransferExpense()
                delay(50)
                homeViewModel.addedExpense.postValue(expense)
            }
    }
}

