package com.funworld.heogiutien.features.add

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import com.funworld.heogiutien.R
import com.funworld.heogiutien.common.*
import com.funworld.heogiutien.model.Expense
import com.funworld.heogiutien.model.Resource
import kotlinx.android.synthetic.main.add_expense_dialog_fragment.*
import java.time.LocalDateTime
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AddExpenseDialogFragment(private val callback: Callback) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    private lateinit var listOfResource: List<Resource>
    private lateinit var createdDateTime: LocalDateTime

    interface Callback {
        fun onAddedExpense(expense: Expense)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.add_expense_dialog_fragment, container, false)
        val toolbar: Toolbar = rootView.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
//        val actionBar = (activity as AppCompatActivity).supportActionBar
//        actionBar?.setDisplayHomeAsUpEnabled(true)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initResource()
        initCreatedTime()
        dialog_layout.setOnClickListener { hideKeyboard() }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        viewModel = ViewModelProviders.of(this).get(AddExpenseViewModel::class.java)
        // TODO: Use the ViewModel
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
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
                    callback.onAddedExpense(getAddedExpense())
                    hideKeyboard()
                    dismiss()
                    true
                } else {
                    toast("Invalid input")
                }
            }
            android.R.id.home -> {
                hideKeyboard()
                dismiss()
                true
            }
            else -> toast("other items " + item.title)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initResource() {
        val adapter =
            ArrayAdapter(
                context!!,
                android.R.layout.simple_spinner_dropdown_item,
                listOfResource?.map { it.name }!!.toTypedArray()
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

    fun setResources(resources: List<Resource>) {
        listOfResource = resources
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
        listOfResource[spinner_resource.selectedItemPosition].id,
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
}

