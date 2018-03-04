package com.funworld.heogiutien.common.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import android.widget.EditText
import com.funworld.heogiutien.R
import kotlinx.android.synthetic.main.dialog_date_time_picker.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.DecimalFormat

/**
 * Created by ThanhTD on 2/16/2018.
 */
class Utils {

    companion object {

        fun asMoneyAmount(amount: Int, unit: String): String {
            val formatter = DecimalFormat("#,###,###")
            return formatter.format(amount) + unit
        }

        fun hideKeyboard(activity: Activity, editText: EditText) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
        }

        fun showDateTimePicker(context: Context, time: Long, listener: (Long) -> Unit) {
            val dateTime = DateTime(time)
            val dialog = Dialog(context)
            with(dialog) {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(true)
                setContentView(R.layout.dialog_date_time_picker)

                //fixme : can not call Ondatechange listener for android below api 26
                date_picker.init(dateTime.year, dateTime.monthOfYear - 1, dateTime.dayOfMonth,
                        { _, year, month, dayOfMonth ->
                            Log.d("DEBUG", "month: $month ")
                            val formatter = DateTimeFormat.forPattern("EEE, dd-MM-yyyy")
                            rd_date.text = formatter.print(DateTime(year, month, dayOfMonth, 0, 0))
                        })

                time_picker.currentHour = dateTime.hourOfDay
                time_picker.currentMinute = dateTime.minuteOfHour

                toggle_date_time.setOnCheckedChangeListener { radioGroup, id ->
                    when (id) {
                        R.id.rd_date -> {
                            time_picker.visibility = View.GONE
                            date_picker.visibility = View.VISIBLE
                        }
                        R.id.rd_time -> {
                            date_picker.visibility = View.GONE
                            time_picker.visibility = View.VISIBLE
                        }

                    }
                }

                time_picker.setOnTimeChangedListener { timePicker, hour, min ->
                    rd_time.text = hour.toString() + ":" + min
                    Log.d("DEBUG", " hour: $hour, min: $min ")
                }


                btn_set_datetime.setOnClickListener {
                    val time = DateTime(date_picker.year, date_picker.month + 1, date_picker.dayOfMonth,
                            time_picker.currentHour, time_picker.currentMinute)
                            .millis
                    listener(time)
                    dismiss()
                }

                show()
            }
        }
    }
}