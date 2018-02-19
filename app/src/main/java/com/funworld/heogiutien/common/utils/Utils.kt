package com.funworld.heogiutien.common.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.text.DecimalFormat

/**
 * Created by ThanhTD on 2/16/2018.
 */
class Utils {

    companion object {

        fun asMoneyAmount(amount: Int): String{
            val formatter = DecimalFormat("#,###,###")
            return formatter.format(amount)+ "k"
        }

        fun hideKeyboard(activity: Activity, editText: EditText){
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
        }
    }
}